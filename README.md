# Project 2 - *News Reader*

**News Reader** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **7** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Begin Date (using a date picker)
  * [x] News desk values (Arts, Fashion & Style, Sports)
  * [x] Sort order (oldest or newest)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any article in results to view the contents in an embedded browser.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:
* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText

The following **bonus** features are implemented:

* [x] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` 
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [x] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [x] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [x] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.

The following **additional** features are implemented:

* [x]  Use MVP architecture to separate presentation layer and data layer
* [x]  Use ReycleView instead of listview
* [x]  Use Fragment instead of new Activity for editing items
* [x]  Use FloatingActionButton instead of normal button
* [x]  Use Constrainlayout instead of Linerlayout and relativelayout

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/usmeng/news_reader/blob/master/screenshoot/news_reader.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Unexpected exception occurred when loading more page by using RxJava 

## Open-source libraries used

- [RxJava](https://github.com/ReactiveX/RxJava) - a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
- [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
- [okhttp](https://github.com/square/okhttp) - An HTTP+HTTP/2 client for Android and Java applications.
- [gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back
- [Glide](https://github.com/Masterminds/glide) - Image loading and caching library for Android

## License

Copyright [2017] [Meng Zhou]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
