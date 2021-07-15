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

import java.util.*;

/** Baş Aşağı İskelet'i deneyen uygulama. */
@Uygulama(adı = "deneme")
public class Deneme {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 2;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 0;
	/** Yaması. */
	public static final int YAMASI = 0;
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
						bilgisi.arşivdenGLFWResmiYükle("resimler/imleç.png"),
						0,
						0));
		
		arayüzü = new Arayüz(bilgisi);
		
		try {
			final List<String> satırlar = bilgisi.satırlarYükle("yazı.deneme");
			yazıDenemesi = "değişken".equalsIgnoreCase(satırlar.get(3)) ?
				new DeğişkenYazıDenemesi(
					satırlar.get(0),
					Integer.parseInt(satırlar.get(1)),
					Integer.parseInt(satırlar.get(2)),
					bilgisi) :
				new DurağanYazıDenemesi(
					satırlar.get(0),
					Integer.parseInt(satırlar.get(1)),
					Integer.parseInt(satırlar.get(2)),
					bilgisi);
			
		} catch (final Exception hata) {
			hata.printStackTrace();
		}
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
