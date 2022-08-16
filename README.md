# SweetHome
SweetHome is a property management system for managers and residents to better communicate with one another
![DashBoardPage](https://github.com/Jubilee101/SweetHome/blob/main/demoResources/sweethome.png)
### Current Features
1. Invoice dashboard with long polling listening to backend for any public/personal invoice update
2. Quick and simple maintenance request sending/handling and public utils reservation/cancellation
3. Discussion board that supports live chat and infinite scrolling!

See video demo here:

[![SweetHome Demo](https://res.cloudinary.com/marcomontalbano/image/upload/v1660682021/video_to_markdown/images/youtube--R0u2Kd4XH1c-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://www.youtube.com/watch?v=R0u2Kd4XH1c "SweetHome Demo")

### Updates
We now support canceling public utils on the resident side! No more worries that you accidentally clicked something!
![CancelReservation](https://github.com/Jubilee101/SweetHome/blob/main/demoResources/deleteReservation.gif)

### Tools and Frameworks
SweetHome mainly uses Spring Boot to build its backend and React.js for the frontend. It also utilized GCS to store images. 
Spring Data JPA is used for better and easier database manipulation.

### Notice
This is the backend implementation of SweetHome, please see frontend [here](https://github.com/Jubilee101/SweetHomeFE): 