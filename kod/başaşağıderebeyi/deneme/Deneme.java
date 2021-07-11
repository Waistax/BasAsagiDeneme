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
import başaşağıderebeyi.kütüphane.günlük.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Baş Aşağı İskelet'i deneyen uygulama. */
@Uygulama
public class Deneme {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 1;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 2;
	/** Yaması. */
	public static final int YAMASI = 7;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	private final UygulamaBilgisi bilgisi;
	
	private int[] dokular;
	private int dokuİmleci;
	private Görselleştirici görselleştiricisi;
	private Yumuşatıcı<Bakış> bakışı;
	private Yumuşatıcı<Görüntü> görüntüsü;
	private DeğişkenYazıGörselleştirici değişkenYazıGörselleştirici;
	
	/** Göstericiyi ve istenen tık oranını sağlar. */
	public Deneme(final UygulamaBilgisi bilgisi) {
		this.bilgisi = bilgisi;
		Gösterici
			.sağla(
				new Gösterici(
					1280,
					720,
					"Deneme: " + SÜRÜM + " İskelet: " + İskelet.SÜRÜM,
					false,
					16,
					1,
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
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					SayaçOlayı.class,
					olay -> saniyeyiSay()));
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
		
		final Gölgelendirici gölgelendirici = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/sıradan.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		final İzdüşüm izdüşüm = new İzdüşüm(new Yöney3(16.0, 9.0, 10.0));
		görselleştiricisi = new Görselleştirici(gölgelendirici, izdüşüm, 1);
		
		bakışı = new Yumuşatıcı<>(new Bakış(), new Bakış(), new Bakış());
		
		dokular = new int[] {
			bilgisi.dokuYükle("resimler/denemeResmi.png"),
			bilgisi.dokuYükle("resimler/tersDenemeResmi.png"),
			bilgisi.dokuYükle("resimler/sabitGenişlikliBüyükYazıŞekli.png") };
		
		final Materyal materyal = new Materyal(
			dokular[dokuİmleci],
			new Yöney4(Yöney4.BİR),
			new Yöney4());
		görüntüsü = new Yumuşatıcı<>(
			new Görüntü(materyal),
			new Görüntü(
				new Materyal(materyal.dokusu, new Yöney4(), new Yöney4())),
			new Görüntü(
				new Materyal(materyal.dokusu, new Yöney4(), new Yöney4())));
		
		final Gölgelendirici yazıGölgelendiricisi = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/değişkenYazı.kgöl",
				"gölgelendiriciler/değişkenYazı.bgöl");
		
		final YazıŞekli yazıŞekli = bilgisi
			.yazıŞekliYükle(
				"resimler/sabitGenişlikliBüyükYazıŞekli.png",
				GL_LINEAR_MIPMAP_LINEAR,
				GL_NEAREST,
				"yazışekilleri/sabitGenişlikliBüyük.yşek");
		
		değişkenYazıGörselleştirici = new DeğişkenYazıGörselleştirici(
			yazıGölgelendiricisi,
			izdüşüm,
			100,
			yazıŞekli,
			10.0);
		değişkenYazıGörselleştirici.boyutunuDeğiştir(0.9);
	}
	
	private void güncelle() {
		bakışı.sakla();
		görüntüsü.sakla();
		
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
	}
	
	private void saniyeyiSay() {
		SistemGünlüğü.KONSOL
			.yaz(
				"Tık Oranı: " +
					İskelet.NESNESİ.tıkHızınıEdin() +
					" Kare Oranı: " +
					İskelet.NESNESİ.kareHızınıEdin());
	}
	
	private void çiz() {
		bakışı.bul();
		görüntüsü.bul();
		görselleştiricisi.ekle(görüntüsü.yumuşatılmışı.dönüşümü);
		görselleştiricisi
			.çiz(bakışı.yumuşatılmışı, görüntüsü.yumuşatılmışı.materyali);
		
		değişkenYazıGörselleştirici.yaz(0.0, 0.0, "Merhaba Dünya!");
		değişkenYazıGörselleştirici.çiz(bakışı.yumuşatılmışı);
	}
	
	@Override
	public String toString() {
		return "Deneme " + SÜRÜM;
	}
}
