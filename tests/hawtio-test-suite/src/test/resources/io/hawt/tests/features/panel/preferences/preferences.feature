@preferences
Feature: Checking the functionality of a Preferences tab

  Scenario Outline: Check that the tabs contain the data
    Given User clicks on "Preferences" option in hawtio drop-down menu
    When User is on "<tab>" tab of Preferences page
    Then The content of Preferences page is open

    Examples:
      |tab          |
      |Home         |
      |Console Logs |
      |Camel        |
      |JMX          |
      |Server Logs  |

    @notHawtioNext @notJBang
    Examples:
      |Sample Plugin|

    @notOnline
    Examples:
      |Connect      |

