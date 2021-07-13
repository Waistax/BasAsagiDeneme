/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 1.4.12 / 13 Tem 2021 / 23:22:34
 */
package başaşağıderebeyi.deneme;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;

/** Yazı denemesini değişken görselleştirici ile çizer. */
public class DeğişkenYazıDenemesi extends YazıDenemesi {
	private final DeğişkenYazıGörselleştirici görselleştirici;
	private final Bakış bakış;
	private final int yazıSayısı;
	
	/** Verilenlerle tanımlar. */
	public DeğişkenYazıDenemesi(
		final String yazı,
		final int yazıSayısı,
		final long tohumu,
		final UygulamaBilgisi bilgisi) {
		super(yazı, yazıSayısı, tohumu);
		final YazıŞekli yazıŞekli = bilgisi
			.yazıŞekliYükle(
				"resimler/büyükYazıŞekli.png",
				GL_LINEAR_MIPMAP_LINEAR,
				GL_NEAREST,
				"yazışekilleri/büyük.yşek");
		final Gölgelendirici gölgelendirici = bilgisi
			.gölgelendiriciYükle(
				"gölgelendiriciler/değişkenYazı.kgöl",
				"gölgelendiriciler/sıradan.bgöl");
		final İzdüşüm izdüşüm = new İzdüşüm();
		bakış = new Bakış();
		görselleştirici = new DeğişkenYazıGörselleştirici(
			yazıŞekli,
			gölgelendirici,
			izdüşüm,
			yazı.length(),
			10.0);
		this.yazıSayısı = yazıSayısı;
	}
	
	@Override
	public void çiz() {
		for (int i = 0; i < yazıSayısı; i++)
			yazıyıEkle(
				boyutları.get(i),
				yatayKonumları.get(i),
				dikeyKonumları.get(i));
		görselleştirici.çiz(bakış);
	}
	
	private void yazıyıEkle(
		final double boyutu,
		final double yatayKonumu,
		final double dikeyKonumu) {
		görselleştirici.boyutunuDeğiştir(boyutu);
		görselleştirici.yaz(yatayKonumu, dikeyKonumu, 0.0, yazısı);
	}
}
