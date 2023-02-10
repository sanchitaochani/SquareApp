# SquareApp

## Build tools & versions used

## Steps to run the app
Clone the repository locally.
Open the project in AndroidStudio
Start emulator (minSdk 23) or attach device to test
Make and run the project.

## What areas of the app did you focus on?
Completion, edge cases, unit testing, clean architecture

## What was the reason for your focus? What problems were you trying to solve?
Completion of features was #1 priority, while maintaining separation of layers. I prioritized unit testing because it's a weak spot and something I need to work on getting better at, one project at a time.

## How long did you spend on this project?
about 4 hours for architecture + UI + features, and about 5 hours on unit testing alone - I ran into a lot of issues here regarding testing coroutines as I'm unfamiliar with the testing setup for coroutines, had to read up a lot of documentation on testing for coroutines and navigate a bunch of errors in the process.

## Did you make any trade-offs for this project? What would you have done differently with more time?
I spent more time on unit testing than I would have liked to, for reasons mentioned above. Given more time, I would definitely spend it on building a better UI (maybe experiment with Jetpack Compose). Currently I have just one generic screen to handle Empty & Error cases. I would prefer to separate those, have better images and descriptions, maybe add some animations, the loading spinner at launch could have been a nice splash screen. 

## What do you think is the weakest part of your project?
unit tests :( I wasn't able to get my tests working quite right just yet but I still learned how to create tests for coroutines, specifically how to test coroutines on the main thread so at least progress was made!

## Did you copy any code or dependencies? Please make sure to attribute them here!
Referenced a bunch of documention on coroutines testing (official docs, blogs, SO) :
https://blog.mindorks.com/mockito-cannot-mock-in-kotlin/
https://stackoverflow.com/questions/54307942/unit-test-with-coroutines-in-kotlinmethod-mylooper-in-android-os-looper-not-mock
https://amitshekhar.me/blog/unit-testing-viewmodel-with-kotlin-coroutines-and-livedata
https://www.geeksforgeeks.org/unit-testing-of-viewmodel-with-kotlin-coroutines-and-livedata-in-android/
https://blog.canopas.com/introduction-to-unit-testing-viewmodels-with-kotlin-coroutine-367630e95831
https://developer.android.com/kotlin/coroutines/test

## Is there any other information you’d like us to know?
I pushed myself to use Dagger in the application and feel much more comfortable setting up DI and creating modules, and although testing was a pain point, I managed to learn quite a bit and fix a bunch of errors with testing main thread. I am hoping to fix my failing tests but if not, you can still see what I got and maybe tell me where I'm going wrong :) 
