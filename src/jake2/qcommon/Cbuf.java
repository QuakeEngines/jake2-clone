/*
 * Cbuf.java
 * Copyright (C) 2003
 * 
 * $Id: Cbuf.java,v 1.13 2004-01-18 10:39:34 rst Exp $
 */
/*
Copyright (C) 1997-2001 Id Software, Inc.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package jake2.qcommon;

import jake2.Globals;
import jake2.game.Cmd;
import jake2.util.Lib;

/**
 * Cbuf
 * TODO complete Cbuf interface
 */
public final class Cbuf {

	/**
	 * 
	 */
	public static void Init() {
		SZ.Init(Globals.cmd_text, Globals.cmd_text_buf, Globals.cmd_text_buf.length);
	}

	public static void InsertText(String text) {

		byte[] temp = null;
		int templen = 0;

		// copy off any commands still remaining in the exec buffer
		templen = Globals.cmd_text.cursize;
		if (templen != 0) {
			temp = new byte[templen];
			System.arraycopy(Globals.cmd_text.data, 0, temp, 0, templen);
			SZ.Clear(Globals.cmd_text);
		}

		// add the entire text of the file
		Cbuf.AddText(text);

		// add the copied off data
		if (templen != 0) {
			SZ.Write(Globals.cmd_text, temp, templen);
			temp = null;
		}
	}

	/**
	 * @param clear
	 */
	static void AddEarlyCommands(boolean clear) {

		for (int i = 0; i < Com.Argc(); i++) {
			String s = Com.Argv(i);
			if (!s.equals("+set"))
				continue;
			Cbuf.AddText("set " + Com.Argv(i + 1) + " " + Com.Argv(i + 2) + "n");
			if (clear) {
				Com.ClearArgv(i);
				Com.ClearArgv(i + 1);
				Com.ClearArgv(i + 2);
			}
			i += 2;
		}
	}

	/**
	 * @return
	 */
	static boolean AddLateCommands() {
		int i;
		int j;
		boolean ret = false;

		// build the combined string to parse from
		int s = 0;
		int argc = Com.Argc();
		for (i = 1; i < argc; i++) {
			s += Com.Argv(i).length();
		}
		if (s == 0)
			return false;

		String text = "";
		for (i = 1; i < argc; i++) {
			text += Com.Argv(i);
			if (i != argc - 1)
				text += " ";
		}

		// pull out the commands
		String build = "";
		for (i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '+') {
				i++;

				for (j = i;(text.charAt(j) != '+') && (text.charAt(j) != '-') && j < text.length(); j++);

				build += text.substring(i, j - 1);
				build += "\n";

				i = j - 1;
			}
		}

		ret = (build.length() != 0);
		if (ret)
			Cbuf.AddText(build);

		text = null;
		build = null;

		return ret;
	}

	/**
	 * @param text
	 */
	public static void AddText(String text) {
		int l = text.length();

		if (Globals.cmd_text.cursize + l >= Globals.cmd_text.maxsize) {
			Com.Printf("Cbuf_AddText: overflow\n");
			return;
		}
		SZ.Write(Globals.cmd_text, text.getBytes(), l);
	}

	/**
	 * 
	 */
	public static void Execute() {

		byte[] text = null;
		byte[] line = new byte[1024];

		Globals.alias_count = 0; // don't allow infinite alias loops

		while (Globals.cmd_text.cursize != 0) {
			// find a \n or ; line break
			text = Globals.cmd_text.data;

			int quotes = 0;
			int i;

			for (i = 0; i < Globals.cmd_text.cursize; i++) {
				if (text[i] == '"')
					quotes++;
				if (!(quotes % 2 != 0) && text[i] == ';')
					break; // don't break if inside a quoted string
				if (text[i] == '\n')
					break;
			}

			System.arraycopy(text, 0, line, 0, i);
			line[i] = 0;

			// delete the text from the command buffer and move remaining commands down
			// this is necessary because commands (exec, alias) can insert data at the
			// beginning of the text buffer

			if (i == Globals.cmd_text.cursize)
				Globals.cmd_text.cursize = 0;
			else {
				i++;
				Globals.cmd_text.cursize -= i;
				byte[] tmp = new byte[Globals.cmd_text.cursize];

				System.arraycopy(text, i, tmp, 0, Globals.cmd_text.cursize);
				System.arraycopy(tmp, 0, text, 0, Globals.cmd_text.cursize);
				text[Globals.cmd_text.cursize] = '\0';

			}

			// execute the command line
			int len = jake2.util.Lib.strlen(line);
			//System.out.println("Cbuf.executelen:" + Globals.cmd_text.cursize + "/" + len);

			String cmd = new String(line, 0, len);
			//System.out.println("Cbuf.data   :" + new String(text,0,16).replace('\n','.').replace('\r','.'));
			//System.out.println("Cbuf.execute:" + xxx.replace('\n','.').replace('\r','.') );
			Cmd.ExecuteString(cmd);

			if (Globals.cmd_wait) {
				// skip out while text still remains in buffer, leaving it
				// for next frame
				Globals.cmd_wait = false;
				break;
			}
		}
	}

	/*
	============
	Cbuf_CopyToDefer
	============
	*/
	public static void CopyToDefer() {
		Lib.memcpy(Globals.defer_text_buf, Globals.cmd_text_buf, Globals.cmd_text.cursize);
		Globals.defer_text_buf[Globals.cmd_text.cursize] = 0;
		Globals.cmd_text.cursize = 0;
	}

	/*
	============
	Cbuf_InsertFromDefer
	============
	*/
	public static void InsertFromDefer() {
		InsertText(new String(Globals.defer_text_buf).trim());
		Globals.defer_text_buf[0] = 0;
	}

}
