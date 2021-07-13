/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.0.0 / 19 Mar 2021 / 14:38:07
 */
package başaşağıderebeyi.deneme;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Baş Aşağı İskelet'i deneyen uygulama. */
@Uygulama
public class Deneme {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 1;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 4;
	/** Yaması. */
	public static final int YAMASI = 1;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	/** Ekranın yatay boyutu. */
	public static final int GENİŞLİK = 1920;
	/** Ekranın dikey boyutu. */
	public static final int YÜKSEKLİK = 1080;
	
	private final UygulamaBilgisi bilgisi;
	
	private Arayüz arayüzü;
	
	private int[] dokular;
	private int dokuİmleci;
	private Görselleştirici görselleştiricisi;
	private Yumuşatıcı<Bakış> bakışı;
	private Yumuşatıcı<Görüntü> görüntüsü;
	private DeğişkenYazıGörselleştirici değişkenYazıGörselleştirici;
	private DurağanYazıGörselleştirici durağanYazıGörselleştirici;
	private Yumuşatıcı<DurağanYazı> durağanYazı;
	
	/** Göstericiyi ve istenen tık oranını sağlar. */
	public Deneme(final UygulamaBilgisi bilgisi) {
		this.bilgisi = bilgisi;
		Gösterici
			.sağla(
				new Gösterici(
					GENİŞLİK,
					YÜKSEKLİK,
					"Deneme: " + SÜRÜM + " İskelet: " + İskelet.SÜRÜM,
					true,
					16,
					0,
					new Yöney3(0.2, 0.0, 0.2)));
		
		İskelet.NESNESİ.istenenTıkHızınıDeğiştir(10.0);
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					OluşturmaOlayı.class,
					olay -> oluştur()));
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					GüncellemeOlayı.class,
					olay -> güncelle()));
//		İskelet.NESNESİ
//			.güncellemeOlaylarınınDağıtıcısınıEdin()
//			.dinleyiciyiEkle(
//				new DinleyiciBilgisi<>(
//					SayaçOlayı.class,
//					olay -> saniyeyiSay()));
		İskelet.NESNESİ
			.çizimOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(ÇizimOlayı.class, olay -> çiz()));
	}
	
	private void oluştur() {
		Gösterici
			.edin()
			.imleciDeğiştir(
				Gösterici
					.edin()
					.imleçOluştur(
						bilgisi.glfwResmiYükle("resimler/imleç.png"),
						0,
						0));
		
		arayüzü = new Arayüz(bilgisi);
		
		final Gölgelendirici gölgelendirici = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/sıradan.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		final İzdüşüm izdüşüm = new İzdüşüm(new Yöney3(16.0, 9.0, 10.0));
		görselleştiricisi = new Görselleştirici(gölgelendirici, izdüşüm, 1);
		
		bakışı = new Yumuşatıcı<>(new Bakış());
		
		dokular = new int[] {
			bilgisi.dokuYükle("resimler/denemeResmi.png"),
			bilgisi.dokuYükle("resimler/tersDenemeResmi.png"),
			bilgisi.dokuYükle("resimler/büyükYazıŞekli.png") };
		
		final Materyal materyal = new Materyal(
			dokular[dokuİmleci],
			new Yöney4(Yöney4.BİR),
			new Yöney4(1.0, 1.0, 1.0, 0.2));
		görüntüsü = new Yumuşatıcı<>(new Görüntü(materyal));
		
		final Gölgelendirici yazıGölgelendiricisi = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/değişkenYazı.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		
		final YazıŞekli yazıŞekli = bilgisi
			.yazıŞekliYükle(
				"resimler/küçükYazıŞekli.png",
				GL_LINEAR_MIPMAP_LINEAR,
				GL_NEAREST,
				"yazışekilleri/küçük.yşek");
		
		değişkenYazıGörselleştirici = new DeğişkenYazıGörselleştirici(
			yazıŞekli,
			yazıGölgelendiricisi,
			izdüşüm,
			100,
			0.0);
		değişkenYazıGörselleştirici.materyali.rengi
			.bileşenleriniDeğiştir(0.9, 0.1, 0.2, 1.0);
		değişkenYazıGörselleştirici.materyali.tabanınınRengi
			.bileşenleriniDeğiştir(0.0, 0.0, 0.0, 0.5);
		değişkenYazıGörselleştirici.boyutunuDeğiştir(0.9);
		
		final Gölgelendirici durağanYazıGölgelendiricisi = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/durağanYazı.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		
		durağanYazıGörselleştirici = new DurağanYazıGörselleştirici(
			durağanYazıGölgelendiricisi,
			izdüşüm);
		
		final DurağanYazı denemeYazısı = new DurağanYazı(
			new BelirliYazıOluşturucu(
				yazıŞekli,
				20.0,
				"Durağan",
				"Yazı",
				"Denemesi"));
		
		durağanYazı = new Yumuşatıcı<>(denemeYazısı);
		durağanYazıGörselleştirici.ekle(durağanYazı.aradeğeri);
		durağanYazı.anlığı.materyali.rengi
			.bileşenleriniDeğiştir(0.1, 0.2, 1.0, 0.75);
	}
	
	private void güncelle() {
		bakışı.sakla();
		görüntüsü.sakla();
		durağanYazı.sakla();
		
		final Girdi girdi = İskelet.NESNESİ.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			İskelet.NESNESİ.durdur();
		
		final Yöney2 hızı = new Yöney2();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_W).basılıOlmasınıEdin())
			hızı.ikinciBileşeni += 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_A).basılıOlmasınıEdin())
			hızı.birinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_S).basılıOlmasınıEdin())
			hızı.ikinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_D).basılıOlmasınıEdin())
			hızı.birinciBileşeni += 0.1;
		bakışı.anlığı.konumu.topla(hızı);
		
		double açısalHız = 0.0;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_Q).basılıOlmasınıEdin())
			açısalHız += 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_E).basılıOlmasınıEdin())
			açısalHız -= 0.1;
		bakışı.anlığı.açısı += açısalHız;
		
		bakışı.anlığı.boyutu += girdi.tekerleğininDevri * 0.1;
		
		hızı.değiştir(Yöney2.SIFIR);
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_UP).basılıOlmasınıEdin())
			hızı.ikinciBileşeni += 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_LEFT).basılıOlmasınıEdin())
			hızı.birinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_DOWN).basılıOlmasınıEdin())
			hızı.ikinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_RIGHT).basılıOlmasınıEdin())
			hızı.birinciBileşeni += 0.1;
		görüntüsü.anlığı.dönüşümü.konumu.topla(hızı);
		
		hızı.değiştir(Yöney2.SIFIR);
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_I).basılıOlmasınıEdin())
			hızı.ikinciBileşeni += 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_J).basılıOlmasınıEdin())
			hızı.birinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_K).basılıOlmasınıEdin())
			hızı.ikinciBileşeni -= 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_L).basılıOlmasınıEdin())
			hızı.birinciBileşeni += 0.1;
		görüntüsü.anlığı.dönüşümü.boyutu.topla(hızı);
		
		açısalHız = 0.0;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_U).basılıOlmasınıEdin())
			açısalHız += 0.1;
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_O).basılıOlmasınıEdin())
			açısalHız -= 0.1;
		görüntüsü.anlığı.dönüşümü.açısı += açısalHız;
		
		görüntüsü.anlığı.materyali.rengi
			.değiştir(görüntüsü.anlığı.dönüşümü.konumu)
			.üçüncüBileşeniniDeğiştir(bakışı.anlığı.boyutu)
			.dördüncüBileşeniniDeğiştir(1.0);
		
		if (girdi.faresininTuşunuEdin(GLFW_MOUSE_BUTTON_1).basılmasınıEdin()) {
			dokuİmleci = ++dokuİmleci % dokular.length;
			görüntüsü.anlığı.materyali.dokusu = dokular[dokuİmleci];
		}
		
		durağanYazı.anlığı.dönüşümü.boyutu.çarp(Math.random() + 0.5);
	}
	
//	private void saniyeyiSay() {
//		SistemGünlüğü.KONSOL
//			.yaz(
//				"Tık Oranı: " +
//					İskelet.NESNESİ.tıkHızınıEdin() +
//					" Kare Oranı: " +
//					İskelet.NESNESİ.kareHızınıEdin());
//	}
	
	private void çiz() {
		bakışı.bul();
		görüntüsü.bul();
		durağanYazı.bul();
		
		görselleştiricisi.ekle(görüntüsü.aradeğeri.dönüşümü);
		görselleştiricisi.çiz(bakışı.aradeğeri, görüntüsü.aradeğeri.materyali);
		
		değişkenYazıGörselleştirici.yaz(0.0, 0.0, -1.0, "Merhaba Dünya!");
		değişkenYazıGörselleştirici
			.ortalıYaz(0.0, 1.0, -1.0, "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZQWX");
		değişkenYazıGörselleştirici
			.tamOrtayaYaz(
				0.0,
				-1.0,
				-1.0,
				"0123456789",
				".,:;-+*/\\!?(){}",
				"[]=<>\"'|#@%_&^₺");
		değişkenYazıGörselleştirici.çiz(bakışı.aradeğeri);
		durağanYazıGörselleştirici.çiz(bakışı.aradeğeri);
		
		arayüzü.çiz();
	}
	
	@Override
	public String toString() {
		return "Deneme " + SÜRÜM;
	}
}
