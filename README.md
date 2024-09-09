# Stori Challenge
The purpose of this application is to show a banking application that allows user registration, user login, bank
information: balance, list of movements made and detail of the movement.

### 📱 App Information
When opening the app, it will show a Login screen with the option to enter user credentials or register a new account. 
If creating a new account is selected, a form will appear to be filled with user's information and then he/she must include a picture of their ID to submit. 
After registration or login process is finished, a Home screen will be shown with the account balance and its movements. 
When selecting one of them, details will be shown on a new screen.

### 👩‍💻 Developer Documentation
1. When opening the app, a Login screen will be shown.
2. If user chooses to enter his/her credentials, a network call to `Firebase` will be done and if it succeeds, app will redirect to Home screen.
3. In case user has chosen Registration option, a Form screen will appear where he/she must complete with their personal information.
4. If all fields are valid, a new screen will open indicating that an ID photo must be included. Camera permissions will be asked (if they are needed), and then camera will be available to be used.
5. If user selects to register, a communication with `Firebase` is stablished and will create a new user on `Firestore`
6. Either login or registration succeeds, a Home screen will be shown with the account balance and the movements ordered by date.
7. If account is new and no movements were done, and empty state will be shown.
8. If user wants, he/she has the opportunity to Log out with an action in the top bar.
9. At Home screen, movements have the possibility to be clicked, thus, the app will redirect to a details screen showing more information.

#### 🔧 Implementation
Project is organized taking care of Clean Architecture organization through `Presentation`, `Domain` and `Data` layers:
- **Presentation**: This layer will respect MVI pattern, where each screen has an `Intent` and a `State`. 
`StateFlows` were implemented with the purpose that screens react when data inside `ViewModel` changes and the purpose of `SharedFlows` are to handle one shot events such as `Toast` error messages and triggering navigation. 
Shimmer effect was implemented using an external library ([Shimmer for Jetpack Compose & Compose Multiplatform](https://github.com/valentinilk/compose-shimmer))
- **Domain**: The only purpose of this layer is to map network models to `View` ones. Moreover, they are in charge to communicate between the different repositories.
For example, take a look to `RegisterUseCase`.
- **Data**: This layer will include `Repositories` and `RemoteDataSources` where the communication with `Firebase` is stablished. 
`Repositories` seem to do no work here, but their purpose will make sense in the future when saving data into Cache or DB. 
At this time, we are not going to implement them. 

### Test User

Email: test@test.com

Password: Pass123!

### 🎬 Recording

https://github.com/user-attachments/assets/aee8a877-7c3f-46ce-a601-fecf46873a63

https://github.com/user-attachments/assets/e22fd72b-6411-4e89-b1ea-8495c7c201ba

