/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 1.4.3 / 13 Tem 2021 / 22:26:29
 */
package başaşağıderebeyi.deneme;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;

/** Yazı denemesini durağan görselleştirici ile çizer. */
public class DurağanYazıDenemesi extends YazıDenemesi {
	private final DurağanYazıGörselleştirici görselleştiricisi;
	private final Bakış bakış;
	private final YazıŞekli yazıŞekli;
	
	/** Verilenlerle tanımlar. */
	public DurağanYazıDenemesi(
		final String yazı,
		final int yazıSayısı,
		final long tohumu,
		final UygulamaBilgisi bilgisi) {
		super(yazı, yazıSayısı, tohumu);
		final Gölgelendirici gölgelendirici = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/durağanYazı.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		final İzdüşüm izdüşüm = new İzdüşüm();
		görselleştiricisi =
			new DurağanYazıGörselleştirici(gölgelendirici, izdüşüm);
		bakış = new Bakış();
		yazıŞekli = bilgisi
			.yazıŞekliYükle(
				"resimler/büyükYazıŞekli.png",
				GL_LINEAR_MIPMAP_LINEAR,
				GL_NEAREST,
				"yazışekilleri/büyük.yşek");
		yazılarıEkle(yazıSayısı);
	}
	
	@Override
	public void çiz() {
		görselleştiricisi.çiz(bakış);
	}
	
	private void yazılarıEkle(final int yazıSayısı) {
		for (int i = 0; i < yazıSayısı; i++)
			yazıyıEkle(
				boyutları.get(i),
				yatayKonumları.get(i),
				dikeyKonumları.get(i));
	}
	
	private void yazıyıEkle(
		final double boyutu,
		final double yatayKonumu,
		final double dikeyKonumu) {
		final DurağanYazı yazı =
			new DurağanYazı(new BelirliYazıOluşturucu(yazıŞekli, 10.0, yazısı));
		görselleştiricisi.ekle(yazı);
		yazı.dönüşümü.boyutu.çarp(boyutu);
		yazı.dönüşümü.konumu.birinciBileşeni = yatayKonumu;
		yazı.dönüşümü.konumu.birinciBileşeni = dikeyKonumu;
	}
}
