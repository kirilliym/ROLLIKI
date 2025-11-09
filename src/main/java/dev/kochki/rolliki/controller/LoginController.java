package dev.kochki.rolliki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value = "/login", produces = "text/html")
    public String loginPage() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Login - Rolliki</title>
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }
                    
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }
                    
                    .login-container {
                        background: white;
                        padding: 40px;
                        border-radius: 20px;
                        box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
                        text-align: center;
                        max-width: 400px;
                        width: 90%;
                    }
                    
                    .logo {
                        font-size: 2.5rem;
                        font-weight: bold;
                        color: #333;
                        margin-bottom: 10px;
                    }
                    
                    .subtitle {
                        color: #666;
                        margin-bottom: 30px;
                        font-size: 1.1rem;
                    }
                    
                    .google-btn {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        background: #4285f4;
                        color: white;
                        padding: 15px 25px;
                        border: none;
                        border-radius: 10px;
                        font-size: 1rem;
                        font-weight: 600;
                        cursor: pointer;
                        text-decoration: none;
                        transition: all 0.3s ease;
                        width: 100%;
                    }
                    
                    .google-btn:hover {
                        background: #357ae8;
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
                    }
                    
                    .google-icon {
                        width: 20px;
                        height: 20px;
                        background: white;
                        border-radius: 50%;
                        margin-right: 12px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-weight: bold;
                        color: #4285f4;
                    }
                    
                    .features {
                        margin-top: 30px;
                        text-align: left;
                    }
                    
                    .feature {
                        display: flex;
                        align-items: center;
                        margin-bottom: 15px;
                        color: #555;
                    }
                    
                    .feature-icon {
                        width: 20px;
                        height: 20px;
                        background: #667eea;
                        border-radius: 50%;
                        margin-right: 10px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        color: white;
                        font-size: 12px;
                    }
                    
                    .footer {
                        margin-top: 25px;
                        color: #888;
                        font-size: 0.9rem;
                    }
                </style>
            </head>
            <body>
                <div class="login-container">
                    <div class="logo">🎯 Rolliki</div>
                    <p class="subtitle">Welcome back! Please sign in to continue</p>
                    
                    <a href="/oauth2/authorization/google" class="google-btn">
                        <div class="google-icon">G</div>
                        Continue with Google
                    </a>
                    
                    <div class="features">
                        <div class="feature">
                            <div class="feature-icon">✓</div>
                            Secure authentication
                        </div>
                        <div class="feature">
                            <div class="feature-icon">✓</div>
                            Fast and reliable
                        </div>
                        <div class="feature">
                            <div class="feature-icon">✓</div>
                            Your data is protected
                        </div>
                    </div>
                    
                    <div class="footer">
                        By continuing, you agree to our Terms of Service
                    </div>
                </div>
            </body>
            </html>
            """;
    }
}