Scenario: Car search with all search parameters empty

Given is the default data set
And an opened search specification page
When I leave all search parameters blank
And I execute the search
Then the web application shows a search result page containing all cars specified in the default data set
