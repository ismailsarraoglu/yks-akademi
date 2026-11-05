package com.yksakademi.app.data

import androidx.room.withTransaction

data class SeedSubject(val exam: String, val name: String, val topics: List<String>)

/**
 * Hazır ders ve konu listeleri. Resmî ÖSYM/MEB listesiyle birebir aynı olduğu garanti edilmez;
 * her konu ve ders uygulama içinden düzenlenebilir, silinebilir veya yenisi eklenebilir.
 */
object SeedData {
    val all: List<SeedSubject> = listOf(
        // ---------------- TYT ----------------
        SeedSubject("TYT", "Türkçe", listOf(
            "Sözcükte Anlam", "Cümlede Anlam", "Paragrafta Anlam", "Anlatım Teknikleri ve Düşünceyi Geliştirme Yolları",
            "Ses Bilgisi", "Yazım Kuralları", "Noktalama İşaretleri", "Sözcükte Yapı (Kök ve Ekler)",
            "Sözcük Türleri: İsim, Sıfat, Zamir, Zarf", "Edat, Bağlaç, Ünlem", "Fiiller ve Ek Fiil", "Fiilimsiler ve Fiil Çatısı",
            "Cümlenin Ögeleri", "Cümle Türleri", "Anlatım Bozuklukları"
        )),
        SeedSubject("TYT", "Matematik", listOf(
            "Temel Kavramlar", "Sayı Basamakları", "Bölme ve Bölünebilme", "EBOB - EKOK", "Rasyonel Sayılar",
            "Basit Eşitsizlikler", "Mutlak Değer", "Üslü Sayılar", "Köklü Sayılar", "Çarpanlara Ayırma",
            "Oran - Orantı", "Denklem Çözme", "Sayı ve Kesir Problemleri", "Yaş Problemleri", "İşçi - Havuz Problemleri",
            "Hareket Problemleri", "Yüzde, Kâr - Zarar Problemleri", "Karışım Problemleri", "Kümeler", "Fonksiyonlar",
            "Polinomlar", "İkinci Dereceden Denklemler", "Permütasyon, Kombinasyon, Olasılık", "Veri ve İstatistik", "Sayısal Mantık"
        )),
        SeedSubject("TYT", "Geometri", listOf(
            "Temel Kavramlar ve Doğruda Açılar", "Üçgende Açılar", "Dik Üçgen ve Özel Üçgenler", "İkizkenar ve Eşkenar Üçgen",
            "Açıortay ve Kenarortay", "Üçgende Alan", "Benzerlik", "Çokgenler", "Dörtgenler",
            "Çember ve Daire", "Analitik Geometri", "Katı Cisimler"
        )),
        SeedSubject("TYT", "Fizik", listOf(
            "Fizik Bilimine Giriş", "Madde ve Özellikleri", "Hareket", "Kuvvet ve Newton'un Hareket Yasaları",
            "İş, Güç ve Enerji", "Isı ve Sıcaklık", "Basınç", "Kaldırma Kuvveti", "Elektrostatik",
            "Elektrik Akımı ve Devreler", "Manyetizma", "Dalgalar", "Optik"
        )),
        SeedSubject("TYT", "Kimya", listOf(
            "Kimya Bilimi", "Atom ve Periyodik Sistem", "Kimyasal Türler Arası Etkileşimler", "Maddenin Halleri",
            "Kimyanın Temel Kanunları", "Mol Kavramı", "Kimyasal Hesaplamalar", "Karışımlar",
            "Asitler, Bazlar ve Tuzlar", "Kimya Her Yerde"
        )),
        SeedSubject("TYT", "Biyoloji", listOf(
            "Canlıların Ortak Özellikleri", "Canlıların Temel Bileşenleri", "Hücre ve Organelleri", "Madde Geçişleri",
            "Canlıların Sınıflandırılması", "Hücre Bölünmeleri", "Kalıtım", "Ekosistem Ekolojisi", "Güncel Çevre Sorunları"
        )),
        SeedSubject("TYT", "Tarih", listOf(
            "Tarih Bilimi ve Zaman", "İlk Çağ Uygarlıkları", "İlk Türk Devletleri", "İslam Tarihi ve Uygarlığı",
            "Türk - İslam Devletleri", "Anadolu'da Türk Hâkimiyeti", "Osmanlı Kuruluş ve Yükselme", "Osmanlı Kültür ve Medeniyeti",
            "Osmanlı Duraklama, Gerileme ve Dağılma", "XX. Yüzyıl Başlarında Osmanlı ve Dünya", "Milli Mücadele",
            "Atatürkçülük ve Türk İnkılabı", "Atatürk Dönemi İç ve Dış Politika", "İkinci Dünya Savaşı ve Soğuk Savaş"
        )),
        SeedSubject("TYT", "Coğrafya", listOf(
            "Doğa ve İnsan", "Dünya'nın Şekli ve Hareketleri", "Harita Bilgisi", "İklim Bilgisi", "Yerin Şekillenmesi",
            "Su, Toprak ve Bitki Coğrafyası", "Nüfus ve Yerleşme", "Türkiye'nin Ekonomik Coğrafyası", "Doğal Afetler", "Çevre ve Toplum"
        )),
        SeedSubject("TYT", "Felsefe", listOf(
            "Felsefenin Konusu", "Bilgi Felsefesi", "Varlık Felsefesi", "Ahlak Felsefesi", "Sanat Felsefesi",
            "Din Felsefesi", "Siyaset Felsefesi", "Bilim Felsefesi", "Mantığa Giriş"
        )),
        SeedSubject("TYT", "Din Kültürü ve Ahlak Bilgisi", listOf(
            "Bilgi ve İnanç", "Din ve İslam", "İslam ve İbadet", "Gençlik ve Değerler", "Allah - İnsan İlişkisi",
            "Hz. Muhammed'in Hayatı", "Vahiy ve Akıl", "Kur'an'da Bazı Kavramlar", "İslam Düşüncesinde Yorumlar", "Din ve Hayat"
        )),

        // ---------------- AYT ----------------
        SeedSubject("AYT", "Matematik", listOf(
            "Fonksiyonlar", "Polinomlar", "İkinci Dereceden Denklemler", "İkinci Dereceden Eşitsizlikler", "Parabol",
            "Trigonometri", "Logaritma", "Diziler", "Permütasyon, Kombinasyon, Binom", "Olasılık", "Limit ve Süreklilik", "Türev", "İntegral"
        )),
        SeedSubject("AYT", "Geometri", listOf(
            "Üçgenler", "Çokgenler ve Dörtgenler", "Çember ve Daire", "Analitik Geometri (Doğru)",
            "Çemberin Analitik İncelenmesi", "Katı Cisimler", "Dönüşüm Geometrisi"
        )),
        SeedSubject("AYT", "Fizik", listOf(
            "Vektörler", "Bağıl Hareket", "Newton'un Hareket Yasaları", "Sabit İvmeli Hareket", "İki Boyutta Hareket (Atışlar)",
            "Enerji ve Hareket", "İtme ve Momentum", "Tork ve Denge", "Elektriksel Kuvvet ve Alan", "Elektriksel Potansiyel ve Sığa",
            "Manyetizma", "Elektromanyetik İndüksiyon", "Alternatif Akım ve Transformatör", "Düzgün Çembersel Hareket",
            "Kütle Çekim ve Kepler Yasaları", "Basit Harmonik Hareket", "Dalga Mekaniği", "Atom Fiziği ve Radyoaktivite", "Modern Fizik"
        )),
        SeedSubject("AYT", "Kimya", listOf(
            "Modern Atom Teorisi", "Gazlar", "Sıvı Çözeltiler ve Çözünürlük", "Kimyasal Tepkimelerde Enerji",
            "Kimyasal Tepkimelerde Hız", "Kimyasal Tepkimelerde Denge", "Asit - Baz Dengesi", "Çözünürlük Dengesi",
            "Kimya ve Elektrik", "Karbon Kimyasına Giriş", "Organik Bileşikler", "Enerji Kaynakları ve Bilimsel Gelişmeler"
        )),
        SeedSubject("AYT", "Biyoloji", listOf(
            "Sinir Sistemi", "Endokrin Sistem", "Duyu Organları", "Destek ve Hareket Sistemi", "Sindirim Sistemi",
            "Dolaşım Sistemi", "Solunum Sistemi", "Boşaltım Sistemi", "Üreme Sistemi ve Embriyonik Gelişim",
            "Nükleik Asitler", "Genetik Şifre ve Protein Sentezi", "Canlılarda Enerji Dönüşümleri", "Bitki Biyolojisi",
            "Komünite ve Popülasyon Ekolojisi", "Biyoteknoloji"
        )),
        SeedSubject("AYT", "Türk Dili ve Edebiyatı", listOf(
            "Güzel Sanatlar ve Edebiyat", "Şiir Bilgisi ve Söz Sanatları", "Hikâye ve Roman", "Tiyatro", "Öğretici Metinler",
            "İslamiyet Öncesi Türk Edebiyatı", "Geçiş Dönemi Türk Edebiyatı", "Halk Edebiyatı", "Divan Edebiyatı",
            "Tanzimat Edebiyatı", "Servet-i Fünun ve Fecr-i Ati", "Milli Edebiyat", "Cumhuriyet Dönemi Türk Edebiyatı", "Edebi Akımlar ve Dünya Edebiyatı"
        )),
        SeedSubject("AYT", "Tarih-1", listOf(
            "Tarih Bilimi", "İlk Türk Devletleri", "İslam Tarihi ve Uygarlığı", "Türk - İslam Devletleri",
            "Anadolu Selçuklu ve Beylikler", "Osmanlı Kuruluş ve Yükselme", "Osmanlı Kültür ve Medeniyeti",
            "Osmanlı Duraklama ve Gerileme", "XIX. Yüzyıl Osmanlı", "Milli Mücadele", "Türk İnkılabı ve Atatürkçülük"
        )),
        SeedSubject("AYT", "Coğrafya-1", listOf(
            "Doğa ve İnsan", "Harita Bilgisi", "İklim ve Yer Şekilleri", "Ekosistem ve Madde Döngüleri", "Biyoçeşitlilik",
            "Nüfus ve Yerleşme", "Türkiye'de Ekonomik Faaliyetler", "Doğal Afetler ve Çevre"
        )),
        SeedSubject("AYT", "Tarih-2", listOf(
            "Eski Çağ Uygarlıkları", "İlk Türk Devletleri", "İslam Medeniyetinin Doğuşu", "Türk - İslam Devletleri",
            "Anadolu'da Türk Siyasi Birliği", "Osmanlı Siyasi Tarihi (Kuruluş - Yükselme)", "Osmanlı Siyasi Tarihi (Duraklama - Dağılma)",
            "Osmanlı Kültür ve Medeniyeti", "Yeni Çağ ve Yakın Çağ Avrupa", "XX. Yüzyıl Başlarında Dünya", "Milli Mücadele",
            "Atatürk Dönemi İç Politika ve İnkılaplar", "Atatürk Dönemi Dış Politika", "İkinci Dünya Savaşı", "Soğuk Savaş Dönemi", "Küreselleşen Dünya"
        )),
        SeedSubject("AYT", "Coğrafya-2", listOf(
            "Ekosistemler ve Biyomlar", "Nüfus Politikaları", "Şehirleşme ve Yerleşme", "Ekonomik Faaliyetlerin Mekânsal Dağılımı",
            "Türkiye'nin Ekonomisi", "Ulaşım Hatları ve Küresel Ticaret", "Türkiye'nin Jeopolitiği", "Küresel Ortam ve Bölgeler",
            "Çevre ve Toplum", "Doğal Kaynaklar ve Sürdürülebilirlik"
        )),
        SeedSubject("AYT", "Felsefe Grubu", listOf(
            "Bilgi Felsefesi", "Varlık Felsefesi", "Ahlak Felsefesi", "Sanat Felsefesi", "Din Felsefesi", "Siyaset Felsefesi",
            "Bilim Felsefesi", "XX. Yüzyıl Felsefesi", "Psikolojinin Temel Süreçleri", "Öğrenme, Bellek ve Düşünme", "Ruh Sağlığı",
            "Birey ve Toplum", "Toplumsal Yapı ve Değişme", "Kültür", "Mantığa Giriş", "Klasik Mantık", "Sembolik Mantık"
        )),
        SeedSubject("AYT", "Din Kültürü ve Ahlak Bilgisi", listOf(
            "Dünya ve Ahiret", "Kur'an'ı Anlama", "İnançla İlgili Meseleler", "İslam ve Bilim", "Anadolu'da İslam",
            "İslam Düşüncesinde Tasavvufi Yorumlar", "Güncel Dini Meseleler", "Yaşayan Dinler"
        )),

        // ---------------- YDT ----------------
        SeedSubject("YDT", "Yabancı Dil (İngilizce)", listOf(
            "Kelime Bilgisi", "Dil Bilgisi", "Okuma", "Paragraf", "Çeviri", "Cümle Tamamlama",
            "Anlam Bütünlüğü", "Diyalog Tamamlama", "Cloze Test", "Anlamca En Yakın Cümle"
        ))
    )
}

object Seeder {
    /** Ders tablosu boşsa hazır ders ve konuları tek işlemde yükler. */
    suspend fun seedIfEmpty(db: AppDatabase) {
        val dao = db.topicDao()
        if (dao.subjectCount() > 0) return
        db.withTransaction {
            val orders = mutableMapOf<String, Int>()
            for (s in SeedData.all) {
                val order = (orders[s.exam] ?: 0) + 1
                orders[s.exam] = order
                val id = dao.insertSubject(Subject(name = s.name, exam = s.exam, orderIndex = order))
                dao.insertTopics(s.topics.map { Topic(subjectId = id, name = it) })
            }
        }
    }
}
