package studentmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.util.EmailValidator;

@WebServlet("/")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO studentDAO;

	public void init() {
		studentDAO = new StudentDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/insert":
				insertStudent(request, response);
				break;
			case "/update":
				updateStudent(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/delete":
				deleteStudent(request, response);
				break;
			default:
				listStudent(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void insertStudent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		Student newStudent = new Student(name, email, address);

		if (EmailValidator.isValid(email)) {
			studentDAO.createStudent(newStudent);
			response.sendRedirect("list");
		} else {
			if (email != null) {
				request.setAttribute("errorMessage", "Invalid email address");
			}
			request.setAttribute("student", newStudent);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/studentForm.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		Student student = new Student(id, name, email, address);

		if (EmailValidator.isValid(email)) {
			studentDAO.updateStudent(student);
			response.sendRedirect("list");
		} else {
			request.setAttribute("errorMessage", "Invalid email address");
			request.setAttribute("student", student);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/studentForm.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Student existingStudent = studentDAO.selectStudent(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/studentForm.jsp");
		request.setAttribute("student", existingStudent);
		dispatcher.forward(request, response);

	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		studentDAO.deleteStudent(id);
		response.sendRedirect("list");

	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Student> listStudent = studentDAO.selectAllStudents();
		request.setAttribute("listStudent", listStudent);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/studentList.jsp");
		dispatcher.forward(request, response);

	}
}