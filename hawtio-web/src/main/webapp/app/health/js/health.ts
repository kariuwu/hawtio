module Health {

    export function HealthController($scope, jolokia, workspace:Workspace, $templateCache) {

      $scope.levelSorting = {
        'ERROR': 0,
        'WARNING': 1,
        'INFO': 2
      };

      $scope.colorMaps = {
        'ERROR': {
          'Health': '#ff0a47',
          'Remaining': '#e92614'
        },
        'WARNING': {
          'Health': '#33cc00',
          'Remaining': '#f7ee09'
        },
        'INFO': {
          'Health': '#33cc00',
          'Remaining': '#00cc33'
        }
      };

      $scope.results = [];
      $scope.responses = {};
      $scope.mbeans = [];
      $scope.displays = [];
      $scope.page = '';

      $scope.pageFilter = '';

      $scope.render = (response) => {
        //log.debug("Got response: ", response);
        var mbean = response.request['mbean'];
        var values = response.value;

        var responseJson = angular.toJson(values);

        if (mbean in $scope.responses) {
          if ($scope.responses[mbean] === responseJson) {
            return;
          }
        }

        $scope.responses[mbean] = responseJson;

        var display = $scope.displays.find((m) => { return m.mbean === mbean });

        values = defaultValues(values);

        values = values.sortBy((value) => {
          if (!value.level) {
            return 99;
          }
          return $scope.levelSorting[value.level];
        });

        values.forEach((value) => {
            value.data = {
              total: 1,
              terms: [{
                term: 'Health',
                count: (<number>(value.healthPercent)).round(3)
              }, {
                term: 'Remaining',
                count: (<number>(1 - value.healthPercent)).round(3)
              }]
            };
            value.colorMap = $scope.colorMaps[value.level];
        });

        if (!display) {
          $scope.displays.push({
            mbean: mbean,
            values: values
          });
        } else {
          display.values = values;
        }

        //log.debug("Display: ", $scope.displays);
        if ($scope.page === '') {
          $scope.page = $templateCache.get('pageTemplate');
        }

        Core.$apply($scope);
      };

      $scope.filterValues = (value) => {
        var json = angular.toJson(value);
        return json.has($scope.pageFilter);
      };

      $scope.$watch('mbeans', (newValue, oldValue) => {
        Core.unregister(jolokia, $scope);
        $scope.mbeans.forEach((mbean) => {
          Core.register(jolokia, $scope, {
            type: 'exec', mbean: mbean,
            operation: "healthList()"
          }, {
            success: $scope.render,
            error: (response) => {
              log.error("Failed to invoke healthList() on mbean: " + mbean + " due to: ", response.error);
              log.info("Stack trace: ", response.stacktrace.split("\n"));
            }
          });
        });
      }, true);

      $scope.getMBeans = () => {
        var healthMap = getHealthMBeans(workspace);
        //log.debug("HealthMap: ", healthMap);
        if (healthMap) {
          if (!angular.isArray(healthMap)) {
            return [healthMap.objectName];
          }
          var answer = healthMap.map((obj) => { return obj.objectName; });
          return answer;
        } else {
          return [];
        }
      };

      $scope.sanitize = (value) => {
        var answer = {};
        Object.extended(value).keys().forEach((key) => {
          if ($scope.showKey(key) && value[key]) {
            answer[key] = value[key];
          }
        });
        return answer;
      };


      $scope.showKey = (key) => {
        if ( key === "colorMap" || key === "data") {
          return false;
        }
        return true;
      };

      $scope.getTitle = (value) => {
        if (value['healthId'].endsWith('profileHealth')) {
          return 'Profile: <strong>' + value['profile'] + '</strong>';
        }
        return 'HealthID: <strong>' + value['healthId'] + '</strong>';
      };

      $scope.mbeans = $scope.getMBeans();

      /**
       * Default the values that are missing in the returned JSON
       */
      function defaultValues(values) {
        angular.forEach(values, (aData) => {
          var domain = aData["domain"];
          if (!domain) {
            var id = aData["healthId"];
            if (id) {
              var idx = id.lastIndexOf('.');
              if (idx > 0) {
                domain = id.substring(0, idx);
                var alias = Health.healthDomains[domain];
                if (alias) {
                  domain = alias;
                }
                var kind = aData["kind"];
                if (!kind) {
                  kind = humanizeValue(id.substring(idx + 1));
                  aData["kind"] = kind;
                }
              }
            }
            aData["domain"] = domain;
          }
        });
        return values;
      }

      function createOKStatus(object) {
        return {
          healthId: object.domain + ".status",
          level: "INFO",
          message: object.title + " is OK"
        };
      }

    }


}
