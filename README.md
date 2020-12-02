# Employee Application:
## Libraries Used
* **Kotlin** programming language
* **Kotlin Coroutines** for async network call
* **Okhttp / Retrofit** for the network layer
* **Gson** Serialization library for JSON deserialization
* **Glide** for image loading
* **RecyclerView** as list view disply
* **MVVM** application model
* **LiveData** for communication between ViewModel and View
* **Truth** for UT
* Try to set up the application structure in the easy way to test and mocking component.
* Also did small preparation when in future easier to scale the application toward modules orientation.

## Main requirements and how they were addressed
* **Take care to properly handle errors returned from the endpoint (or other network errors like timeouts)**
  * Okhttp object is set up with restriction for timeout in connection and read data from server.
  
* **All screens and views which load from the network should display proper loading, empty, and error states when content is not available.**
  * Progress bar to show if application is loading data or not.
  * Error Toast will show up in case of data loads failed.
  * If nothing in the list (or error) a text will show and let user know nothing on the list.
  * The data in malform will be ignore (whole list).
  
* **Photo loading and caching**
  * Handled by Glide, with it's default strategy of `AUTOMATIC` it should cache the the images loaded from the server.
  * The small photo URL was used to load up the employee thumbnails and a placeholder is included for employees with no images.
  
* **The employee list**
  * Recycler view.
  * Only load once when the application is launch (rotate won't trigger other network call).
  * List is sort by name (alphabetical order).
  * Using glide for display image (using glide pre-build caching image), if image load faild, use pre-set image to show.
  
* **Testing**
  * Using truth library for a bit better readabilty.
  * UT is priority method (since quicker and better mock).
  * Instrumentation mostly for testing UI.
  * Coverage about 70% (not include Intrumentation test) - Result base on run UT with coverage on Android Studio, may need to combine by Jacoco for clearer number.
  * Note some function is intent to be separate or visible for easier to mock (we can improve this by using some better DI such as Dagger 2).
  
* **Tablet / phone focus**
  * Phone Pixel 2 (emulator) - Api 28
  
  
* **Note**
  * AnnotatedDeserializer, this class, I used some reference from this post for process required field in Gson.By adding one custom annotation.
  https://stackoverflow.com/questions/21626690/gson-optional-and-required-fields
  