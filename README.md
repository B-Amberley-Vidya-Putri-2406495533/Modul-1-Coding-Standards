## REFLEKSI 1
Sesuai dengan yang sudah diajarkan, saya membuat fitur 
edit dan delete product menggunakan Springboot dengan arsitektur 
yang terdiri dari Controller, Service, dan Repository. 
Struktur ini membuat flow kode lebih mudah dipahami. 

Saat mengembangkan fitur edit dan delete,
saya mengimplementasikan **clean code** seperti:
1. Setiap layer struktur memiliki fungsi yang jelas. 
Dimana controller menangani request, service menangani logika, 
dan repository menangani data. 
2. Penamaan masing-masing class dan function jelas dan self explanatory
3. Setiap metode jelas dan hanya menangani satu tugas utama
4. Terdapat unit dan functional testing untuk memastikan fitur berjalan dengan lancar

Selain itu, saya menerapkan **secure coding** yaitu 
1. Penggunaan UUID yang membuat id dibuat otomatis sehingga 
mengurangi risiko duplicates 
2. Data diakses melalui service jadi controller tidak langsung
mengakses data
