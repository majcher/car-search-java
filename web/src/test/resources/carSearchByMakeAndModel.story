Scenario: Car search by make and model

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input car make <make>
And I input car model <model>
And I execute the search
Then the web application shows a search result page showing cars <make> <model> in size of <count>

Examples:
|phrase|make|model|count|
|Ford Mustang|Ford|Mustang|1|
|Buick Lacrosse|Buick|Lacrosse|1|
|BMW X5|BMW|X5|1|
|Audi A4|Audi|A4|2|

