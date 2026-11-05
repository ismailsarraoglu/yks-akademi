# YKS AKADEMİ (Aşama 1)

Tamamen çevrimdışı çalışan Android YKS hazırlık uygulaması.
Kotlin · Jetpack Compose · Room · MVVM · Material 3. INTERNET izni yoktur.

## Bu aşamada çalışanlar
- Apple tarzı açık/koyu tema, alt navigasyon (5 sekme)
- Ana sayfa paneli: hedef, sınava kalan gün, günlük hedef çubukları, özet kartlar, son 7 gün grafiği, hızlı erişim kartları
- Ayarlar: ad, hedef üniversite/bölüm, sınav yılı/tarihi, günlük-haftalık hedefler, yanlış katsayısı, tema (Room'a kaydedilir)
- Tam Room şeması (konu, soru, süre, deneme, günlük/haftalık plan, tekrar, tercih tabloları)
- Diğer modüller "Aşama X'te eklenecek" yer tutucusu olarak görünür

## APK'yı GitHub ile üretme (bilgisayara Android Studio kurmadan)
1. https://github.com adresinde ücretsiz hesap aç, **New repository** ile boş bir depo oluştur (Public veya Private).
2. Depo sayfasında **uploading an existing file** bağlantısına tıkla.
3. Bu klasörün **içindeki her şeyi** (`app`, `.github`, `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `.gitignore`, `README.md`) sürükleyip bırak, **Commit changes** de.
   - `.github` gizli klasör olduğu için sürüklemede atlanabilir. Yüklemeden sonra depoda `.github/workflows/build-apk.yml` görünmüyorsa: **Add file → Create new file**, dosya adı kutusuna `.github/workflows/build-apk.yml` yaz ve dosyanın içeriğini yapıştır.
4. Üstteki **Actions** sekmesine gir. "APK Derle" iş akışı otomatik başlar (başlamazsa soldan seç → **Run workflow**).
5. 5-10 dakika sonra yeşil tik çıkınca çalıştırmanın içine gir, altta **Artifacts** bölümünden `YKS-Akademi-debug-apk` dosyasını indir ve ZIP'i aç.
6. `app-debug.apk` dosyasını telefona aktar, aç. "Bilinmeyen kaynaklardan yükleme" izni istenirse ver.

Derleme kırmızı biterse: Actions → başarısız çalıştırma → hata veren adımı aç → kırmızı hata satırlarını kopyalayıp bana gönder.

## Android Studio ile derleme (isteğe bağlı)
Klasörü Android Studio ile aç, Gradle eşitlemesini bekle, **Build → Build APK(s)**.

## Notlar
- Varsayılan sınav tarihi (2027-06-19) tahminidir. Ayarlar'dan resmî tarihe göre düzenle.
- Sonraki aşamalar: 2 Konu/Soru takibi · 3 Kronometre, planlar, tekrar · 4 Deneme, tercih, raporlar · 5 JSON/CSV yedek.
