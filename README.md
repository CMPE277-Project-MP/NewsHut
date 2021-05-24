# NewsHut - Android News Application developed using Kotlin
By 
Team10
- Mamatha Guntu 
- Prajakta Joshi

### NewsHut is a smart news application that helps users to find daily news on various categories.

### Features in the application:
1.	Display top headlines.
2.	Detail screen to view the news article 
3.	Search News based on keywords
4.	Mark News as Favorites
5.	Post News 
6.	Firebase authentication with email, password.

### Implementation Details:
-	Software architectural pattern used for implementing the application is Model-View-ViewModel.
-	API used to fetch news details “http://newsapi.org”. Endpoint used to fetch the headlines:  /v2/top-headlines
-	Amplify storage S3 is used to store the user posted story related picture
- Retrofit is used to make the service calls to fetch the JSON response.
-	Moshi – used for parsing JSON data.
- Recycler view with embedded cardview to display the list of news.
- Created adapter class to map the data from the service to the view.
- Firebase is used for authentication 
- AWS NoSQL datastore DynamoDB used to store user favorite stories and posted stories
- AWS Lambda functions are used to access the data in DynamoDB
- AWS API Gateway is used to deploy the lambda function and get the api url 

Screenshots:

AWS  LAMBDA FUNCTIONS
![image](https://user-images.githubusercontent.com/2658837/119383200-e3911b80-bc77-11eb-940a-4e771ed29f10.png)


AWS API GATEWAY
 Each api contains both get and post api 
![image](https://user-images.githubusercontent.com/2658837/119383188-e0962b00-bc77-11eb-9049-6ce5b7028d82.png)


Data is stored in NoSQL database , AWS DynamoDB
 
![image](https://user-images.githubusercontent.com/2658837/119383157-db38e080-bc77-11eb-8659-a66e44266ec4.png)


User posted story related pictures are stored in aws s3 bucket
 
![image](https://user-images.githubusercontent.com/2658837/119383135-d6742c80-bc77-11eb-9358-1f14d0c0fda2.png)



- Login 
- 
<img src ="https://user-images.githubusercontent.com/2658837/119383265-f7d51880-bc77-11eb-9c61-a2eb281d0e7d.png" width="300" height="600"/>
<img src ="https://user-images.githubusercontent.com/2658837/119383302-03284400-bc78-11eb-8754-f2b858a66f5c.png" width="300" height="600"/>

- Register 
- <img src ="https://user-images.githubusercontent.com/2658837/119383293-ff94bd00-bc77-11eb-94e1-bba07fce36d1.png" width="300" height="600"/>

- top headlines
- <img src ="https://user-images.githubusercontent.com/2658837/119383317-08858e80-bc78-11eb-928d-8b334247de5e.png" width="300" height="600"/>

- detailed view 
- <img src ="https://user-images.githubusercontent.com/2658837/119383333-0d4a4280-bc78-11eb-858f-a21aad320c77.png" width="300" height="600"/>

- mark favorite
- <img src ="https://user-images.githubusercontent.com/2658837/119383355-12a78d00-bc78-11eb-9971-bcf6fe2aa665.png" width="300" height="600"/>

- view favorite news
- <img src ="https://user-images.githubusercontent.com/2658837/119383368-16d3aa80-bc78-11eb-9f42-05c888692b8d.png" width="300" height="600"/>

- sports option in drawer menu
- <img src ="https://user-images.githubusercontent.com/2658837/119383389-1c30f500-bc78-11eb-9111-10b31025bebb.png" width="300" height="600"/>

- view sports news 
- <img src ="https://user-images.githubusercontent.com/2658837/119383397-1fc47c00-bc78-11eb-9910-2f0fb3dcb8f7.png" width="300" height="600"/>

- post story
- <img src ="https://user-images.githubusercontent.com/2658837/119383410-24893000-bc78-11eb-8cea-3bb2d8eb8f35.png" width="300" height="600"/>

- posted stories
- ![image](https://user-images.githubusercontent.com/2658837/119384299-564ec680-bc79-11eb-8c89-d82b18abbc08.png)


Reference Links:
●	Recycler view - https://developer.android.com/guide/topics/ui/layout/recyclerview
●	Firebase - https://firebase.google.com/docs/android/setup#console
 

Git repo: https://github.com/CMPE277-Project-MP/NewsHut.git

Contributions:
●	Mamatha Guntu : 
○	Firebase authentication for register, login
○	Detail view of a selected news article
○	Post a story to the News App
●	Prajakta Joshi: 
○	Listing news in Home screen based on different categories-sports
○	Mark news as favorites,
○	Search News based on Keywords

Reference Links:
●	Recycler view - https://developer.android.com/guide/topics/ui/layout/recyclerview
●	Firebase - https://firebase.google.com/docs/android/setup#console
