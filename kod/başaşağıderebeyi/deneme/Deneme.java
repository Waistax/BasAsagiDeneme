/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.0.0 / 19 Mar 2021 / 14:38:07
 */
package başaşağıderebeyi.deneme;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Baş Aşağı İskelet'i deneyen uygulama. */
@Uygulama
public class Deneme {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 0;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 0;
	/** Yaması. */
	public static final int YAMASI = 0;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	@Dinleyici
	public void oluşturmaOlayınıDinle(OluşturmaOlayı olay) {
		
	}
	
	@Dinleyici
	public void yokEtmeOlayınıDinle(YokEtmeOlayı olay) {
		
	}
	
	@Dinleyici
	public void güncellemeOlayınıDinle(GüncellemeOlayı olay) {
		
	}
}
