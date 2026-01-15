feature/orhan  →  develop  →  master
commitlerin akışı...

Problem : “Kullanıcı dosya yüklesin, dosya diske yazılsın, metadata DB’ye kaydedilsin”

UPLOAD MODULU : 
NE YAPAR : DOSYAYI ALIR , METADATA BİLGİLERİ FİZİKSEL VERİTABANINA KAYDEDER,
NE YAPMAZ: LİSTELEME , SİLME , GÜNCELLEME YAPMAZ !!!


UPLOAD MODULÜ : YALNIZCA DOSYA KABUL ETME VE KAYDETME İŞLEMİNDEN SORUMLU OLMALI (SRP)
Listeleme, silme, indirme = farklı use-case, farklı aksiyonlar
Bunları tek endpoint’e koymak → şişman controller / servis

HER ENDPOİNT BİR İŞ YAPSIN ONU EN MÜKEMMEL ŞEKİLDE YAPSIN.. SRP (SİNGLE RESPONSİBİLİTY )
************************************

Her modül şu 4 ana paketi içerir:

api           → dış dünya (HTTP)
application   → use-case / service
domain        → iş kuralları, entity, value object
infra         → DB, disk, dış sistem

*******
ENDPOINT AYRIMI : PROBLEMDEN ENDPOİNTLERİ AYIR

UPLOAD FİLES KAYNAĞININ BİR AKSİYONUDUR..

ENDPOİNTLER
POST   /files        -> upload  UPLOAD MODULU SADECE DOSYA YÜKLER,KAYIT EDER
GET    /files        -> list
GET    /files/{id}   -> download
DELETE /files/{id}  -> delete



*********

HTTP STATUS KODLARI : 

| Durum                                  | Doğru Kod         |
| -------------------------------------- | ----------------- |
| Upload başarılı                        | **201 Created** ✅ |
| Upload başarılı ama içerik dönmüyorsan | 204               |
| Validation hatası                      | 400               |
| Dosya çok büyük                        | 413               |
| Server çöktü                           | 500               |


*******

Upload aksiyonu çalışırken aşağıdaki adımların HANGİLERİ OLMALI?
❌ Dosya sistemi (disk) transactional değildir
Yani:
DB rollback olur
Ama disk’e yazılan dosya geri alınmaz
👉 O yüzden “tek bir @Transactional her şeyi çözer” düşüncesi YANLIŞ.

1. Request gelir
2. Controller → sadece HTTP + validation
3. Service başlar (business logic)
4. Dosya TEMP alana yazılır
5. Dosya doğrulanır (size, type)
6. DB transaction başlar
7. Metadata DB’ye kaydedilir
8. Transaction commit
9. Dosya FINAL alana taşınır
10. Response 200

🔁 FAILURE SENARYOSU
****
❌ DB fail olursa?
1.Temp dosya silinir
2.DB rollback olur
3.Response 500

****
❌ Dosya final alana taşınamazsa?
1.DB kaydı silinir (compensating action)
2.Temp dosya silinir
3.Response 500

👉 Buna Compensating Transaction denir.


*******************************

🧠 DOĞRU AYRIM (AKLINA KAZI)

| Katman     | Ne Bilir                       |
| ---------- | ------------------------------ |
| Controller | `MultipartFile`, HTTP, Request |
| Service    | **Pure Java**, business rules  |
| Infra      | Disk, Path, File               |

👉 Service katmanı framework bilmez.