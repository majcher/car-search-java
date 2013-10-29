Scenario: Car search

Given is the default data set
When I execute a search with more than one search result
Then the page should contain for each found car the image, make, model, color and the price

