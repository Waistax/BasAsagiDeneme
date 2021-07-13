/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 1.4.3 / 13 Tem 2021 / 22:26:44
 */
package başaşağıderebeyi.deneme;

import java.util.*;

/** Rastgele yazı denemeleri oluşturur ve çizer. */
public abstract class YazıDenemesi {
	/** Yazdığı yazı. */
	protected final String yazısı;
	/** Farklı yazımların boyutları. */
	protected final List<Double> boyutları;
	/** Farklı yazımların yatay konumları. */
	protected final List<Double> yatayKonumları;
	/** Farklı yazımların dikey konumları. */
	protected final List<Double> dikeyKonumları;
	
	private final Random rastgelesi;
	
	/** Verilenlerle tanımlar. */
	public YazıDenemesi(
		final String yazı,
		final int yazıSayısı,
		final long tohumu) {
		yazısı = yazı;
		boyutları = new ArrayList<>();
		yatayKonumları = new ArrayList<>();
		dikeyKonumları = new ArrayList<>();
		rastgelesi = new Random(tohumu);
		yazılarıOluştur(yazıSayısı);
	}
	
	private void yazılarıOluştur(final int yazıSayısı) {
		for (int i = 0; i < yazıSayısı; i++)
			yazıOluştur();
	}
	
	private void yazıOluştur() {
		boyutları.add(boyutBul());
		yatayKonumları.add(konumOluştur() - 0.1);
		dikeyKonumları.add(konumOluştur() * 2.0);
	}
	
	private double boyutBul() {
		return rastgelesi.nextDouble() / 10.0;
	}
	
	private double konumOluştur() {
		return (rastgelesi.nextDouble() - 0.5) / 1.0;
	}
	
	/** Deneme yazılarını çizer. */
	public abstract void çiz();
}
