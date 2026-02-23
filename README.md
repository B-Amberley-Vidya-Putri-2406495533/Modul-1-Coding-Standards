### REFLEKSI 1

Sesuai dengan yang sudah diajarkan, saya membuat fitur edit dan delete product menggunakan
Spring Boot dengan arsitektur yang terdiri dari Controller, Service, dan Repository.
Struktur ini membuat flow kode lebih mudah dipahami.

Saat mengembangkan fitur edit dan delete, saya mengimplementasikan **clean code** seperti:

1. Setiap layer struktur memiliki fungsi yang jelas, di mana controller menangani request,
   service menangani logika, dan repository menangani data.
2. Penamaan masing-masing class dan function jelas dan self explanatory.
3. Setiap metode jelas dan hanya menangani satu tugas utama.
4. Terdapat unit dan functional testing untuk memastikan fitur berjalan dengan lancar.

Selain itu, saya menerapkan **secure coding** yaitu:

1. Penggunaan UUID yang membuat ID dibuat otomatis sehingga mengurangi risiko duplicates.
2. Data diakses melalui service sehingga controller tidak langsung mengakses data.

---

### REFLEKSI 2

***After writing the unit test, how do you feel?***
Saya merasa lebih yakin bahwa program yang dibuat berjalan dengan baik.

***How many unit tests should be made in a class? How to make sure that our unit tests are enough?***
Banyaknya unit test tergantung dengan class yang ada, tetapi agar aman biasanya meng-cover
beberapa kasus seperti success, failed, dan edge case lainnya yang mungkin ada.

***Does 100% code coverage mean no bugs?***
Walaupun mencapai 100% coverage, ini tidak menjamin bahwa program tidak terdapat bug.
Masih mungkin terdapat logical error, kesalahan desain, atau kasus yang belum terpikirkan
dalam pengujian. Bahkan mungkin terdapat kesalahan juga dalam testing-nya.

***Cleanliness of New Functional Test Suite:***
Jika dibuat functional test baru, kemungkinan besar akan terjadi pengulangan kode,
terutama pada bagian setup seperti konfigurasi `baseUrl` dan inisialisasi driver. Hal ini
dapat menyebabkan duplikasi kode, kurangnya reusabilitas, dan meningkatnya kompleksitas
program.

***Improvements:***
Untuk menjaga kebersihan kode, beberapa perbaikan yang dapat dilakukan adalah membuat
kelas dasar untuk setup umum, menggunakan helper method, dan mengurangi duplikasi kode.

---
### REFLEKSI MODULE 2
1) ***Code quality issues I fixed and my strategy***

   Issue 1: Empty test method (java:S1186: “Methods should not be empty”)
   in `EshopApplicationTests.contextLoads()`, I kept the method empty on purpose 
   because it’s a standard Spring Boot “context loads” test but to fix it I added a clear 
   comment explaining that its job is to verify the Spring context can start successfully. 
   My strategy was to read the issue see the fix, and add a simple comment explaining what it does 
   and later when i pushed it again, the issue was no longer there.
   There were also several other issues reported by SonarCloud during the analysis process. 
   However, some of those issues were related to the scoreboard code that originated directly 
   from the provided GitHub template. Since those parts were not modified and were outside the scope 
   of the implemented functionality, i consider them irrelevant and were not addressed.


2) ***Does my CI/CD meet Continuous Integration and Continuous Deployment?***

   Yes, the current setup meets Continuous Integration because every push and pull request automatically 
   triggers the workflows to build the project, run unit tests, and produce coverage reports. 
   It also meets Continuous Deployment because after changes are merged to main branch, Koyeb automatically 
   pulls the latest code and redeploys the application.

**LINK DEPLOYMENT:** https://dependent-haleigh-b-amberley-vidya-putri-2406495533-665252ef.koyeb.app/