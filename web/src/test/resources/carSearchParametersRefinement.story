Scenario: Car search parameters refinement

Given is the default data set
And an opened search result page for parameters - make: <make> model: <model>, color: <color>
When I want to refine search
And I click some kind of button to return to the search specification page
Then the web application shows the search specification page with pre filled search parameters regarding the last search - make: <make> model: <model>, color: <color>

Examples:
|make|model|color|
|Audi|A4|British racing green|
|Buick|Lacrosse|Midnight blue|