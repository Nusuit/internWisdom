Write some CRUD APIs with Spring Boot

Steps to test your APIs:
Create User Account (No ADMIN Authentication Needed):
You can create a user account without needing ADMIN authentication.

![image](https://github.com/user-attachments/assets/adec67b2-326b-4691-bed5-5c35695f1858)

Generate JWT Token:
This is the URL for generating your JWT token for authentication.

![image](https://github.com/user-attachments/assets/44c1aa8c-ce8b-4936-8c56-9a0796efa571)

Create Admin Account (Requires JWT Token):
When creating an account for an admin, you must provide a valid JWT token in the request header.

![image](https://github.com/user-attachments/assets/f4c48ee8-60f6-4428-b2fc-33913cdf3276)

Perform Other Actions (ADMIN Authentication Required):
Most of the API actions (GET, UPDATE, DELETE) require ADMIN authentication. Make sure to include the JWT token in the request headers for these operations.

![image](https://github.com/user-attachments/assets/9c3b21be-dad2-4777-968d-5f7267bf54bf)

![img.png](img.png)

![img_1.png](img_1.png)