/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 1.4.1 / 13 Tem 2021 / 21:11:29
 */
package başaşağıderebeyi.deneme;

import static başaşağıderebeyi.deneme.Deneme.*;
import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.text.*;

/** Denemenin arayüzü. Kare oranını çizer. */
class Arayüz {
	/** Arayüz izdüşümünün derinliği */
	public static final double DERİNLİK = 1.0;
	/** Kare hızı göstergesinin yazı boyutu. */
	public static final double KARE_HIZI_BOYUTU = 16.0;
	/** Kare hızı göstergesinin yatay konumu. */
	public static final double KARE_HIZI_YATAY_KONUMU =
		(KARE_HIZI_BOYUTU - GENİŞLİK) / 2.0;
	/** Kare hızı göstergesinin dikey konumu. */
	public static final double KARE_HIZI_DİKEY_KONUMU =
		YÜKSEKLİK / 2.0 - (KARE_HIZI_BOYUTU * 1.5);
	/** Kare hızı göstergesinin derinliği. */
	public static final double KARE_HIZI_DERİNLİĞİ = -DERİNLİK / 2.0 * 0.999999;
	/** Kare hızı göstergesinin biçimi. */
	public static final DecimalFormat KARE_HIZI_GÖSTERGESİ =
		new DecimalFormat("0000HZ");
	
	private final İzdüşüm izdüşümü;
	private final Bakış bakışı;
	private final YazıŞekli yazısınınŞekli;
	private final Gölgelendirici yazıGölgelendiricisi;
	private final DeğişkenYazıGörselleştirici yazarı;
	
	Arayüz(UygulamaBilgisi uygulamaBilgisi) {
		izdüşümü = new İzdüşüm(new Yöney3(GENİŞLİK, YÜKSEKLİK, DERİNLİK));
		bakışı = new Bakış();
		yazısınınŞekli = uygulamaBilgisi
			.yazıŞekliYükle(
				"resimler/küçükYazıŞekli.png",
				GL_LINEAR_MIPMAP_LINEAR,
				GL_NEAREST,
				"yazışekilleri/küçük.yşek");
		yazıGölgelendiricisi = uygulamaBilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/değişkenYazı.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		yazarı = new DeğişkenYazıGörselleştirici(
			yazısınınŞekli,
			yazıGölgelendiricisi,
			izdüşümü,
			6,
			0.0);
		yazarı.boyutunuDeğiştir(KARE_HIZI_BOYUTU);
	}
	
	void çiz() {
		yazarı
			.yaz(
				KARE_HIZI_YATAY_KONUMU,
				KARE_HIZI_DİKEY_KONUMU,
				KARE_HIZI_DERİNLİĞİ,
				KARE_HIZI_GÖSTERGESİ.format(İskelet.NESNESİ.kareHızınıEdin()));
		yazarı.çiz(bakışı);
	}
}
