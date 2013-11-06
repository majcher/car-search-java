Scenario: Car search with no results

Given is the default data set
And an opened search specification page
When I input car make <make>
And I execute the search
Then the web application shows an empty search result page with the message "sorry, no cars found"

Examples:
|make|
|Trabant|