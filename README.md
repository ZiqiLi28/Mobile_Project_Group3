# SimplyTally

**DIN22SP Group 3**

## Description

Welcome to **SimplyTally**.
It is a financial management app developed using Kotlin. It's designed to help users manage their finances with ease. Whether you want to track your daily expenses, monitor your income, or visualize your financial data, SimplyTally has got you covered. The app aims to simplify financial tracking by offering intuitive features and a user-friendly interface.

## Technology Architecture

  For the user interface:
- **Model-View-ViewModel (MVVM) Architecture**
- **Jetpack Compose**
  For backend services:
- **Firebase Authentication**
  For data serialization:
- **Gson library**
  For data visualization:
- **MPAndroidChart**
  For testing:
- **Unit Testing and Instrumented Testing**

## Features

- **Accounting:** Users can select specific situations to record either their income or expenses. This allows for more detailed and organized financial tracking.

- **Display record information:** Users can access detailed information by clicking on the transaction to view the time, amount and details.

- **Chart display:** The homepage features a chart where users can click to view their monthly income and expenses represented in icon form. This visual representation makes it easier to understand spending habits and income trends over time.

- **Search bar:** SimplyTally includes a search bar that allows users to quickly find specific records by typing keywords. This feature saves time and helps users locate transactions efficiently.

- **Account:** Users can create their own accounts within the app. This feature enables personalized financial tracking.

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/ZiqiLi28/Mobile_Project_Group3.git
   ```

2. Open the project in Android Studio.

3. Run the app either on an Android emulator or a physical Android device connected to your computer.

## Division of Work

**Minyi Zhang:**

1. Layout Design:
   Designing and implementing the overall UI/UX of the app.
   Creating layouts for different screens and user interactions.
2. Fragments
   Implementing chart related fragments.
   Implementing fragments for expense and income.
3. Adapters
   Developing and managing adapters for displaying data in lists or grids.

**Lufei Wu:**

1. Utility Classes:
   Creating utility classes for common functionalities and reusable code.
2. Account Management with Firebase
   Integrating Firebase for user authentication and account management.
   Setting up user registration, login, and profile management functionalities using Firebase.
3. Fragments
   Working on records fragments, integrating the expense and income fragment, managing the record types for the transactions.

**Ziqi Li:**

1. Page Functions
   Implementing various page functions, mainly the activities on home page.
2. Database Management
   Setting up and managing the app's database.
   Implementing CRUD (Create, Read, Update, Delete) operations for data persistence.
   Ensuring data integrity and security in the database.

## License

SimplyTally is an open-source project licensed under the MIT License. This means that you are free to use, modify, and distribute the app's source code according to the terms specified in the MIT License.

### Development Team

- **[Ziqi Li](https://github.com/ZiqiLi28)**
- **[Lufei Wu](https://github.com/lufeiwu22)**
- **[Minyi Zhang](https://github.com/minyizhangg)**
