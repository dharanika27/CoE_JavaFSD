
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class BookNotFoundException extends Exception {
	public BookNotFoundException(String msg) {
		super(msg);
	}
}

class UserNotFoundException extends Exception {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}

class MaxBooksAllowedException extends Exception {
	public MaxBooksAllowedException(String msg) {
		super(msg);
	}
}

class Book implements Serializable {
	private String title, author, ISBN;
	private boolean isBorrowed;

	public Book(String title, String author, String ISBN) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.isBorrowed = false;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getISBN() {
		return ISBN;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean status) {
		this.isBorrowed = status;
	}

	@Override
	public String toString() {
		return title + " by " + author + " (ISBN: " + ISBN + ") - " + (isBorrowed ? "Borrowed" : "Available");
	}
}

class User implements Serializable {
	private String name, userID;
	private List<Book> borrowedBooks;

	public User(String name, String userID) {
		this.name = name;
		this.userID = userID;
		this.borrowedBooks = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getUserID() {
		return userID;
	}

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void borrowBook(Book book) {
		borrowedBooks.add(book);
	}

	public void returnBook(Book book) {
		borrowedBooks.remove(book);
	}

	@Override
	public String toString() {
		return name + " (ID: " + userID + ") - Borrowed Books: " + borrowedBooks.size();
	}
}

interface ILibrary {
	void borrowBook(String ISBN, String userID)
			throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;

	void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;

	void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;

	Book searchBook(String title);
}

abstract class LibrarySystem implements ILibrary {
	protected List<Book> books = new ArrayList<>();
	protected List<User> users = new ArrayList<>();

	public abstract void addBook(Book book);

	public abstract void addUser(User user);
}

class LibraryManager extends LibrarySystem {
	private final Map<String, Book> bookMap = new ConcurrentHashMap<>();
	private final Map<String, User> userMap = new ConcurrentHashMap<>();
	private final Object lock = new Object();

	public LibraryManager() {
		loadLibraryData();
	}

	public void addBook(Book book) {
		books.add(book);
		bookMap.put(book.getISBN(), book);
	}

	public void addUser(User user) {
		users.add(user);
		if (user != null)
			userMap.put(user.getUserID(), user);
	}

	public void borrowBook(String ISBN, String userID)
			throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
		synchronized (lock) {
			if (!bookMap.containsKey(ISBN))
				throw new BookNotFoundException("Book not found!");
			if (!userMap.containsKey(userID))
				throw new UserNotFoundException("User not found!");

			User user = userMap.get(userID);
			Book book = bookMap.get(ISBN);

			if (book.isBorrowed())
				throw new MaxBooksAllowedException("Book already borrowed!");
			if (user.getBorrowedBooks().size() >= 3)
				throw new MaxBooksAllowedException("Limit exceeded (Max 3 books)!");

			book.setBorrowed(true);
			user.borrowBook(book);
			System.out.println(user.getName() + " borrowed " + book.getTitle());
		}
	}

	public void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
		synchronized (lock) {
			if (!bookMap.containsKey(ISBN))
				throw new BookNotFoundException("Book not found!");
			if (!userMap.containsKey(userID))
				throw new UserNotFoundException("User not found!");

			User user = userMap.get(userID);
			Book book = bookMap.get(ISBN);

			if (!user.getBorrowedBooks().contains(book))
				throw new BookNotFoundException("User didn't borrow this book!");

			book.setBorrowed(false);
			user.returnBook(book);
			System.out.println(user.getName() + " returned " + book.getTitle());
		}
	}

	public void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
		if (!bookMap.containsKey(ISBN))
			throw new BookNotFoundException("Book not found!");
		if (!userMap.containsKey(userID))
			throw new UserNotFoundException("User not found!");

		System.out.println(userID + " reserved " + bookMap.get(ISBN).getTitle());
	}

	public Book searchBook(String title) {
		for (Book book : books) {
			if (book.getTitle().equalsIgnoreCase(title))
				return book;
		}
		return null;
	}

	public void saveLibraryData() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("libraryData.dat"))) {
			out.writeObject(books);
			out.writeObject(users);
		} catch (IOException e) {
			System.out.println("Error saving library data!");
		}
	}

	public void loadLibraryData() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("libraryData.dat"))) {
			books = (List<Book>) in.readObject();
			users = (List<User>) in.readObject();
			books.forEach(book -> bookMap.put(book.getISBN(), book));
			users.forEach(user -> userMap.put(user.getUserID(), user));
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("No previous library data found.");
		}
	}
}

public class LibraryMain {
	public static void main(String[] args) {
		LibraryManager libManager = new LibraryManager();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n1. Add Book\n2. Add User\n3. Borrow Book\n4. Return Book\n5. Search Book\n6. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (choice) {
				case 1:
					System.out.print("Enter book title: ");
					String title = scanner.nextLine();
					System.out.print("Enter author: ");
					String author = scanner.nextLine();
					System.out.print("Enter ISBN: ");
					String isbn = scanner.nextLine();
					libManager.addBook(new Book(title, author, isbn));
					break;

				case 2:
					System.out.print("Enter user name: ");
					String name = scanner.nextLine();
					System.out.print("Enter user ID: ");
					String userID = scanner.nextLine();
					libManager.addUser(new User(name, userID));
					break;

				case 3:
					System.out.print("Enter ISBN to borrow: ");
					String borrowISBN = scanner.nextLine();
					System.out.print("Enter User ID: ");
					String borrowUser = scanner.nextLine();
					libManager.borrowBook(borrowISBN, borrowUser);
					break;

				case 4:
					System.out.print("Enter ISBN to return: ");
					String returnISBN = scanner.nextLine();
					System.out.print("Enter User ID: ");
					String returnUser = scanner.nextLine();
					libManager.returnBook(returnISBN, returnUser);
					break;

				case 5:
					System.out.print("Enter book title to search: ");
					Book foundBook = libManager.searchBook(scanner.nextLine());
					System.out.println(foundBook != null ? foundBook : "Book not found!");
					break;

				case 6:
					libManager.saveLibraryData();
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}