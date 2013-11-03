Scenario: Car search by color

Given is the default data set
And an opened search specification page
When I want to search for some <color> tone
And I input some <color> tone
And I execute the search
Then the web application shows a search result page showing list of cars: <cars>

Examples:
|color|cars|
|red|Persimmon red Ford Mustang, Scarlet red Audi A4|
|blue|Midnight blue Buick Lacrosse, Ocean blue BMW X5|
|green|British racing green Audi A4|
