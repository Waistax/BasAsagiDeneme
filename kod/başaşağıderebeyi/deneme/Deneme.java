/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.0.0 / 19 Mar 2021 / 14:38:07
 */
package başaşağıderebeyi.deneme;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Baş Aşağı İskelet'i deneyen uygulama. */
@Uygulama
public class Deneme {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 0;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 4;
	/** Yaması. */
	public static final int YAMASI = 3;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	private final UygulamaBilgisi bilgisi;
	
	private KöşeDizisi köşeDizisi;
	private int tekerleğinToplamDevri;
	private float boyutu;
	private float öncekiBoyutu;
	private float çizilecekBoyutu;
	private Gölgelendirici gölgelendiricisi;
	private DeğişkenYazıGörselleştirici yazar;
	
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
					0,
					1,
					new Yöney4(0.2F, 0.0F, 0.2F, 0.0F)));
		
		İskelet.NESNESİ.istenenTıkOranınıDeğiştir(10.0);
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
		
		köşeDizisi = new KöşeDizisi(GL_TRIANGLE_STRIP);
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				2,
				memAllocFloat(8)
					.put(-0.5F)
					.put(-0.5F)
					.put(+0.5F)
					.put(-0.5F)
					.put(-0.5F)
					.put(+0.5F)
					.put(+0.5F)
					.put(+0.5F));
		
		final Yöney4 solAltKöşeninRengi = new Yöney4(1.0F, 0.0F, 0.0F, 1.0F);
		final Yöney4 sağAltKöşeninRengi = new Yöney4(0.0F, 0.0F, 1.0F, 1.0F);
		final Yöney4 solÜstKöşeninRengi = new Yöney4(1.0F, 0.0F, 0.0F, 1.0F);
		final Yöney4 sağÜstKöşeninRengi = new Yöney4(0.0F, 0.0F, 1.0F, 1.0F);
		
		if (true) {
			solAltKöşeninRengi.rgbdenHsluva();
			sağAltKöşeninRengi.rgbdenHsluva();
			solÜstKöşeninRengi.rgbdenHsluva();
			sağÜstKöşeninRengi.rgbdenHsluva();
		}
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				4,
				memAllocFloat(16)
					.put(solAltKöşeninRengi.birinciBileşeni)
					.put(solAltKöşeninRengi.ikinciBileşeni)
					.put(solAltKöşeninRengi.üçüncüBileşeni)
					.put(solAltKöşeninRengi.dördüncüBileşeni)
					.put(sağAltKöşeninRengi.birinciBileşeni)
					.put(sağAltKöşeninRengi.ikinciBileşeni)
					.put(sağAltKöşeninRengi.üçüncüBileşeni)
					.put(sağAltKöşeninRengi.dördüncüBileşeni)
					.put(solÜstKöşeninRengi.birinciBileşeni)
					.put(solÜstKöşeninRengi.ikinciBileşeni)
					.put(solÜstKöşeninRengi.üçüncüBileşeni)
					.put(solÜstKöşeninRengi.dördüncüBileşeni)
					.put(sağÜstKöşeninRengi.birinciBileşeni)
					.put(sağÜstKöşeninRengi.ikinciBileşeni)
					.put(sağÜstKöşeninRengi.üçüncüBileşeni)
					.put(sağÜstKöşeninRengi.dördüncüBileşeni));
		
		gölgelendiricisi = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/renkliDikdörtgen.kgöl",
				"gölgelendiriciler/renkliDikdörtgen.bgöl");
		gölgelendiricisi.bağla();
		gölgelendiricisi.değerinKonumunuBul("boyutu");
		
		yazar = new DeğişkenYazıGörselleştirici(
			100,
			bilgisi
				.yazıŞekliYükle(
					"resimler/sabitGenişlikliBüyükYazıŞekli.png",
					"yazışekilleri/sabitGenişlikliBüyük.yşek"),
			10.0F,
			new Dizey4().izdüşümDizeyineÇevir(1280.0F, 720.0F, 20.0F),
			0.9F,
			bilgisi
				.gölgelendiriciYükle(
					"gölgelendiriciler/değişkenYazı.kgöl",
					"gölgelendiriciler/değişkenYazı.bgöl"));
		
		yazar.boyutunuDeğiştir(30.0F);
		yazar.renginiEdin().bileşenleriniDeğiştir(0.1F, 0.1F, 0.7F, 1.0F);
	}
	
	private void güncelle() {
		final ÇiğGirdi girdi = İskelet.NESNESİ.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			İskelet.NESNESİ.durdur();
		
		tekerleğinToplamDevri += girdi.tekerleğininDevri;
		öncekiBoyutu = boyutu;
		boyutu = (float)pow(1.05, tekerleğinToplamDevri);
	}
	
	private void saniyeyiSay() {
		System.out
			.println(
				"Tık Oranı: " +
					İskelet.NESNESİ.tıklarınınOranınıEdin() +
					" Kare Oranı: " +
					İskelet.NESNESİ.karelerininOranınıEdin());
	}
	
	private void çiz() {
		gölgelendiricisi.bağla();
		çizilecekBoyutu = aradeğerleriniBul(
			öncekiBoyutu,
			boyutu,
			(float)İskelet.NESNESİ.güncellenmemişTıklarınıEdin());
		
		gölgelendiricisi.değeriDeğiştir("boyutu", çizilecekBoyutu);
		köşeDizisi.çiz();
		gölgelendiricisi.kopar();
		
		yazar
			.yaz(
				-100.0F,
				300.0F,
				"Kare Oranı: " +
					İskelet.NESNESİ.karelerininOranınınOrtalamasınıEdin());
		yazar.çiz();
	}
	
	@Override
	public String toString() {
		return "Deneme " + SÜRÜM;
	}
}
