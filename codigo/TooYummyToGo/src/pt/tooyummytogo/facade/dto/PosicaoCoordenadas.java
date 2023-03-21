package pt.tooyummytogo.facade.dto;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

public class PosicaoCoordenadas {

	private double latitude;
	private double longitude;

	/**
	 * Construtor de PosicaoCoordenadas
	 * @param lat latitude da localizacao
	 * @param lng longitude da localizacao
	 */
	public PosicaoCoordenadas(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	/**
	 * Devolve latitude
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Devolve longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Devolve a distancia em metros entre esta posicao e a dada
	 * @param o posicao com PosicaoCoordenadas dado
	 * @return distancia em metros entre as posicoes
	 */
	public double distanciaEmMetros(PosicaoCoordenadas o) {
		GeodesicData g = Geodesic.WGS84.Inverse(this.latitude, this.longitude, o.latitude, o.longitude);
		return g.s12;  // distance in metres
	}

}
