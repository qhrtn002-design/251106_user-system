package org.example.usersystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.usersystem.service.UserAccountService;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserAccountService accountService = new UserAccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean loginSuccess = accountService.login(username, password);
        if (loginSuccess) {
            resp.sendRedirect(req.getContextPath() + "/"); // 로그인 성공 시 main
        } else {
            resp.sendRedirect(req.getContextPath() + "/login"); // 재시도하도록...
        }
    }
}