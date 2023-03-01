<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <html>

        <head>
            <title>User Management Application</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>
        
        <style>
        	.error-message{
				background: #FFC199; /*Change background color*/
				border-left: 9px solid #FF6600; /*Change left border color*/
				color: #2c3e50; /*Change text color*/
			}
        	</style>

        <body>

            <header>
                <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #00355f">
                    <div>
                        <a href="#" class="navbar-brand"> Student Management System </a>
                    </div>

                    <ul class="navbar-nav">
                        <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Students</a></li>
                    </ul>
                </nav>
            </header>
            <br>
            <div class="container col-md-5">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${student.id != null && student.id != 0}">
                            <form action="update" method="post">
                        </c:if>
                        <c:if test="${student.id == null || student.id == 0}">
                            <form action="insert" method="post">
                        </c:if>

                        <caption>
                            <h2>
                                <c:if test="${student.id != null && student.id != 0}">
                                    Edit Student
                                </c:if>
                                <c:if test="${student.id == null || student.id == 0}">
                                    Add New Student
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${student.id != null && student.id != 0}">
                            <input type="hidden" name="id" value="<c:out value='${student.id}' />" />
                        </c:if>

                        <fieldset class="form-group">
                            <label>Student Name</label> <input type="text" value="<c:out value='${student.name}' />" class="form-control" name="name" required="required">
                        </fieldset>
						
						<% if (request.getAttribute("errorMessage") != null ) { %>
							<h5 class="error-message"><%=request.getAttribute("errorMessage") %></h5>
						<% } %>
						
                        <fieldset class="form-group">
                            <label>Student Email</label> <input type="text" value="<c:out value='${student.email}' />" class="form-control" name="email" required="required">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Student Address</label> <input type="text" value="<c:out value='${student.address}' />" class="form-control" name="address">
                        </fieldset>

                        <button type="submit" class="btn btn-success">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </body>

        </html>