/**
 *
 * EventoZero - Advanced event factory and executor for Bukkit and Spigot.
 * Copyright © 2016 BlackHub OS and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.blackhubos.eventozero.storage;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Storage
{

	/**
	 * Obter um valor que está linkado a um jogador em um serviço.
	 *
	 * @param service Em qual serviço isso está? bans? pontos? etc.
	 * @param player Nome do jogador
	 * @param key A key que você deseja obter o valor
	 * @return Retorna null se não existir a key ou o valor para o jogador; ou um objeto caso exista.
	 */
	public abstract <T> T getPlayerData(Module service, String player, String key);

	/**
	 * Serve para agregar um valor a um jogador, para "marcar" ele.
	 *
	 * @param service Em qual serviço isso afeta? bans? pontos? etc.
	 * @param player Nome do jogador
	 * @param key A key desejada para marcar (para ser usada ou pegar depois)
	 * @param value O objeto que deseja linkar a key
	 */
	public abstract void setPlayerData(Module service, String player, String key, Object value);

	/**
	 * Acrescenta pontos do ranking de um jogador.
	 *
	 * @param player nome do jogador
	 * @param evento nome do evento
	 * @param tipo acrescentar em qual dos diferentes tipos de pontos? 0 = derrotas; 1 = vitórias; 2 = desconectou; 3 = morreu
	 * @param value o valor a acrescentar.
	 */
	public abstract void depositPlayerRankingPoints(String player, String evento, int tipo, long value);

	/**
	 * Remove pontos do ranking de um jogador.
	 *
	 * @param player nome do jogador
	 * @param evento nome do evento
	 * @param tipo debitar em qual dos diferentes tipos de pontos? 0 = derrotas; 1 = vitórias; 2 = desconectou; 3 = morreu
	 * @param value o valor a remover (poderá ficar negativo).
	 */
	public abstract void withdrawPlayerRankingPoints(String player, String evento, int tipo, long value);

	/**
	 * Obtém a quantia de pontos que um jogador tem em um evento em X modalidade (tipo).
	 *
	 * @param player nome do jogador
	 * @param evento nome do evento
	 * @param tipo debitar em qual dos diferentes tipos de pontos? 0 = derrotas; 1 = vitórias; 2 = desconectou; 3 = morreu
	 * @return Retorna a quantia se ele tiver, ou 0 se ele não tiver nada.
	 */
	public abstract long getPlayerRankingPoints(String player, String evento, int tipo);

	/**
	 * Efetuar uma busca na database de eventos.
	 *
	 * @param query O código-mysql para a busca.
	 * @return Uma {@link ResultSet} referente a busca.
	 */
	public abstract ResultSet search(String query) throws SQLException;

	public static enum Module implements Serializable
	{
		// id | jogador | evento | vitorias | derrotas | dc | mortes
		RANKING("rankings", "rankings.yml"),

		// id | jogador | evento | -OPCIONAL- motivo
		BAN("bannedplayers", "bans.yml"),

		// id | jogador | pontos
		POINT("points", "points.yml"),

		// id | evento | mundo | localização (String revertida via Framework!!!)
		SIGN("signs", "signs.yml"),

		// id | jogador | evento| devolvido | vida | comida | xp | level | local | itens | armadura
		BACKUP("backup", ""),

		// id | key | valor
		OTHER("others", "");

		private final String table;
		private final String flatfile;

		Module(final String t, final String f)
		{
			this.table = t;
			this.flatfile = f;
		}

		public String getTable()
		{
			return this.table;
		}

		public String getFlatfile()
		{
			return this.flatfile;
		}
	}

}
