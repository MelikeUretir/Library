package app;

import dao.BookDao;
import dao.LoanDao;
import dao.StudentDao;
import entity.Book;
import entity.Loan;
import entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static BookDao bookDao = new BookDao();
    static StudentDao studentDao = new StudentDao();
    static LoanDao loanDao = new LoanDao();

    public static void main(String[] args) {
        // Log kalabalığını azaltmak için (İsteğe bağlı)
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        while (true) {
            System.out.println("\n============ SMART LIBRARY PLUS ============");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Buffer temizle

                switch (choice) {
                    case 1: addBook(); break;
                    case 2: listBooks(); break;
                    case 3: addStudent(); break;
                    case 4: listStudents(); break;
                    case 5: borrowBook(); break;
                    case 6: listLoans(); break;
                    case 7: returnBook(); break;
                    case 0:
                        System.out.println("Çıkış yapılıyor...");
                        System.exit(0);
                    default:
                        System.out.println("Geçersiz seçim.");
                }
            } catch (Exception e) {
                System.out.println("Hatalı giriş! Lütfen sayı giriniz.");
                scanner.nextLine();
            }
        }
    }

    private static void addBook() {
        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yıl: ");
        int year = scanner.nextInt();

        Book newBook = new Book(title, author, year, "AVAILABLE");
        bookDao.save(newBook);
        System.out.println(">>> Kitap eklendi!");
    }

    private static void listBooks() {
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) {
            System.out.println("Listelenecek kitap yok.");
        } else {
            System.out.println("--- KİTAP LİSTESİ ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    private static void addStudent() {
        System.out.print("Öğrenci Adı: ");
        String name = scanner.nextLine();
        System.out.print("Bölüm: ");
        String department = scanner.nextLine();

        Student newStudent = new Student(name, department);
        studentDao.save(newStudent);
        System.out.println(">>> Öğrenci eklendi!");
    }

    private static void listStudents() {
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("Listelenecek öğrenci yok.");
        } else {
            System.out.println("--- ÖĞRENCİ LİSTESİ ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private static void borrowBook() {
        listStudents();
        System.out.print("Öğrenci ID seçin: ");
        Long sId = scanner.nextLong();

        listBooks();
        System.out.print("Kitap ID seçin: ");
        Long bId = scanner.nextLong();

        Book book = bookDao.getById(bId);
        Student student = studentDao.getById(sId);

        if (book != null && student != null) {
            if ("BORROWED".equals(book.getStatus())) {
                System.out.println("!!! HATA: Bu kitap zaten ödünç verilmiş.");
            } else {
                Loan loan = new Loan(student, book, LocalDate.now());
                loanDao.save(loan);

                book.setStatus("BORROWED");
                bookDao.update(book);

                System.out.println(">>> İşlem Başarılı: Kitap ödünç verildi.");
            }
        } else {
            System.out.println("!!! HATA: Geçersiz Öğrenci veya Kitap ID.");
        }
    }

    private static void listLoans() {
        List<Loan> loans = loanDao.getAll();
        if (loans.isEmpty()) {
            System.out.println("Ödünç kaydı bulunamadı.");
        } else {
            System.out.println("--- ÖDÜNÇ HAREKETLERİ ---");
            for (Loan l : loans) {
                String status = (l.getReturnDate() == null) ? "Teslim Edilmedi" : "İade Tarihi: " + l.getReturnDate();
                System.out.println("Kitap: " + l.getBook().getTitle() +
                        " -> Alan: " + l.getStudent().getName() +
                        " [" + status + "]");
            }
        }
    }

    private static void returnBook() {
        System.out.print("İade edilecek Kitap ID'sini girin: ");
        Long bId = scanner.nextLong();

        Loan activeLoan = loanDao.getActiveLoanByBookId(bId);

        if (activeLoan != null) {
            activeLoan.setReturnDate(LocalDate.now());
            loanDao.update(activeLoan);

            Book book = activeLoan.getBook();
            book.setStatus("AVAILABLE");
            bookDao.update(book);

            System.out.println(">>> Kitap başarıyla iade alındı.");
        } else {
            System.out.println("!!! Bu kitaba ait aktif bir ödünç kaydı yok.");
        }
    }
}