TASK

- Splash is the opening screen which checks if the user is logged in or not.
- If the user is logged in detail screen is opened which shows the profile pic, email and password
- If the user is not logged in, Login Screen is opened for the user to enter the email the password.
- There is basic check on email and password.
- Once the valid input is given and user pressed SignIn button. Login api is called and on successful signin the credentials are saved to shared prefs. And then the user is navigated to the Detail page
- In Detail page the profile pic is shown if avaiable, else it tries to load a gravatar profile pic based on the email. If that is also not present a profile drawable is shown.
- User can change the profile pic on clicking on the image. Which shows two options to choose the photo from (Camera/Gallery)
- If user chooses camera option, Camera is opened and on taking the photo the bitmap is compressed to reduce the size within 1MB size and then uploaded to server via api
- If user choose gallery option, Gallery is opened and on choosing the pic and then uploaded to server via api


STRETCH:
1. Done
2. Done: Used centercrop to achieve it.
3. Done: Applied a red filter on the image to show
4. impoemented MVVM framework

QUESTIONS
1. MVVM framework is used.
  Libraries used:
   - Android libraries (Material, core, preferences, coroutines,
   - Glide (for image)
   - Retrofit( for api calls)
   - Koin (for Dependency injection)
   - Mockito and Espresso for testing.
2. Used Glide to set up a placeholder and fade animation once url is loaded. Also used override options to o
3. I setup the logic on splash screen which is the starting screen. And checking if I have a token or not. Based on the condition i can know if the user is logged in or not
4. I used https://jsonplaceholder.typicode.com/ to create a mock server with required request and response. And also followed TDD to tackle the problem of no server.
5. Used Espresso and Mockito to write the tests. Followed TDD approach to complete the features as the server wasn't available to me but the requirements were.