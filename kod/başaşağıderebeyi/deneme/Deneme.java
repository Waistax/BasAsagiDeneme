/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.0.0 / 19 Mar 2021 / 14:38:07
 */
package başaşağıderebeyi.deneme;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
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
	public static final int YAMASI = 10;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	/** Ekranın yatay boyutu. */
	public static final int GENİŞLİK = 1920;
	/** Ekranın dikey boyutu. */
	public static final int YÜKSEKLİK = 1080;
	
	private final UygulamaBilgisi bilgisi;
	
	private Arayüz arayüzü;
	private YazıDenemesi yazıDenemesi;
	
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
		yazıDenemesi = new DurağanYazıDenemesi(
			"Bugün fıstıkçı şahap değil jöleci yavuz alsa olur mu?",
			1000,
			0,
			bilgisi);
	}
	
	private void güncelle() {
		final Girdi girdi = İskelet.NESNESİ.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			İskelet.NESNESİ.durdur();
	}
	
	private void çiz() {
		yazıDenemesi.çiz();
		arayüzü.çiz();
	}
	
	@Override
	public String toString() {
		return "Deneme " + SÜRÜM;
	}
}
