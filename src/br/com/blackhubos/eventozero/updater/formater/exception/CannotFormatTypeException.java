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
package br.com.blackhubos.eventozero.updater.formater.exception;

/**
 * Erro da tentativa de fazer parse de um objeto
 */
public class CannotFormatTypeException extends RuntimeException {

    public CannotFormatTypeException() {
        super();
    }

    public CannotFormatTypeException(String message) {
        super(message);
    }

    public CannotFormatTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFormatTypeException(Throwable cause) {
        super(cause);
    }

}
