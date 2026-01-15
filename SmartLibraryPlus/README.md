# SmartLibraryPlus - Akıllı Kütüphane Sistemi (Hibernate ORM + SQLite)

## Öğrenci Bilgileri
- Ad – Soyad: Melike Üretir
- Öğrenci Numarası: 20230108040

## Projenin Amacı
Bu proje, final ödevi kapsamında aşağıdaki konuları uygulamalı olarak göstermek için hazırlanmıştır:
- Nesneye Yönelik Programlama (OOP)
- ORM (Object Relational Mapping) mantığı
- Hibernate ile veritabanı işlemleri
- Entity–Relationship (ER) yapıları
- CRUD operasyonları (DAO katmanı ile)

## Kullanılan Teknolojiler
- Java
- Hibernate ORM (Annotation tabanlı mapping)
- SQLite
- Maven
- Konsol (CLI) uygulaması

## Entity'ler ve Alanlar

### 1) Book (Kitap)
- `id`
- `title`
- `author`
- `year`
- `status` (AVAILABLE / BORROWED)

### 2) Student (Öğrenci)
- `id`
- `name`
- `department`

Bir öğrencinin birden fazla ödünç alma kaydı olabilir.

### 3) Loan (Ödünç Alma)
- `id`
- `borrowDate`
- `returnDate`

## İlişkiler (Zorunlu)
- Student – Loan : OneToMany / ManyToOne
- Loan – Book : OneToOne

Projede `@OneToMany`, `@ManyToOne`, `@OneToOne` annotation’ları kullanılmıştır.

## Veritabanı
- Veritabanı: SQLite
- Tablolar Hibernate tarafından otomatik oluşturulur.
- `hbm2ddl.auto=update` ayarı kullanılmıştır.

## DAO Katmanı (Zorunlu)
Her entity için ayrı DAO sınıfı vardır:
- `BookDao`
- `StudentDao`
- `LoanDao`

Her DAO sınıfında aşağıdaki metotlar bulunur:
- `save()`
- `update()`
- `delete()`
- `getById()`
- `getAll()`

DAO içinde Hibernate `Session` ve `Transaction` kullanılmıştır.

## Uygulama Menüsü (Zorunlu)
Uygulama çalıştığında konsolda aşağıdaki menü gösterilir:

1 - Kitap Ekle  
2 - Kitapları Listele  
3 - Öğrenci Ekle  
4 - Öğrencileri Listele  
5 - Kitap Ödünç Ver  
6 - Ödünç Listesini Görüntüle  
7 - Kitap Geri Teslim Al  
0 - Çıkış

## İşlevler (Zorunlu Senaryo)

### 1) Kitap Ekle
- title, author, year bilgileri alınır
- status başlangıçta `AVAILABLE` olarak atanır

### 2) Kitapları Listele
- Tüm kitaplar listelenir
- Durum bilgisi (status) ekranda gösterilir

### 3) Öğrenci Ekle
- name ve department bilgileri alınır

### 4) Öğrencileri Listele
- Tüm öğrenciler listelenir

### 5) Kitap Ödünç Ver
- Student ID ve Book ID alınır
- borrowDate alınır
- Eğer kitap `BORROWED` ise işlem yapılmaz
- Başarılı olursa kitap status `BORROWED` olarak güncellenir ve Loan kaydı oluşturulur

### 6) Ödünç Listesini Görüntüle
Listede şu bilgiler gösterilir:
- Öğrenci adı
- Kitap adı
- Alış tarihi
- İade tarihi (null olabilir)

### 7) Kitap Geri Teslim Al
- returnDate güncellenir
- Kitap status tekrar `AVAILABLE` yapılır

## Çalıştırma
1. Projeyi bilgisayarına indir/klonla
2. Maven dependency'leri indir (IDE otomatik indirir)
3. `app.Main` sınıfını çalıştır
