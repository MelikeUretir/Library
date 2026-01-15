# SmartLibraryPlus - ORM Tabanlı Akıllı Kütüphane Sistemi

Bu proje, Java programlama dili kullanılarak geliştirilmiş konsol tabanlı bir kütüphane yönetim uygulamasıdır. Hibernate ORM ve SQLite ile çalışır. Kullanıcı menü üzerinden kitap ve öğrenci kayıtlarını yönetebilir, kitap ödünç verme/teslim alma işlemlerini gerçekleştirebilir.

## Temel Özellikler
- Kitap ekleme ve kitapları listeleme (durum: AVAILABLE / BORROWED)
- Öğrenci ekleme ve öğrencileri listeleme
- Kitap ödünç verme (kitap BORROWED ise işlem yapılmaz)
- Ödünç kayıtlarını görüntüleme (öğrenci adı, kitap adı, alış tarihi, iade tarihi)
- Kitap geri teslim alma (returnDate güncellenir, kitap tekrar AVAILABLE olur)

## Kullanılan Teknolojiler
- Java
- Hibernate ORM (Annotation tabanlı mapping)
- SQLite
- Maven

## Veri Modeli ve İlişkiler
- Student → Loan : OneToMany / ManyToOne
- Loan → Book : OneToOne
