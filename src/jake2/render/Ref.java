/*
 * Ref.java
 * Copyright (C) 2003
 *
 * $Id: Ref.java,v 1.2 2003-11-24 20:27:41 cwei Exp $
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
package jake2.render;

import jake2.client.refexport_t;
import jake2.client.refimport_t;

/**
 * Ref
 * 
 * @author cwei
 */
public interface Ref extends refexport_t {
	
	// ============================================================================
	// ref.h
	// ============================================================================
	static final int MAX_DLIGHTS = 32;
	static final int MAX_ENTITIES = 128;
	static final int MAX_PARTICLES = 4096;
	static final int MAX_LIGHTSTYLES = 256;

	static final float POWERSUIT_SCALE = 4.0f;

	static final int SHELL_RED_COLOR =	0xF2;
	static final int SHELL_GREEN_COLOR = 0xD0;
	static final int SHELL_BLUE_COLOR = 0xF3;

	static final int SHELL_RG_COLOR = 0xDC;
//	  static final int SHELL_RB_COLOR = 0x86;
	static final int SHELL_RB_COLOR = 0x68;
	static final int SHELL_BG_COLOR = 0x78;

//	  ROGUE
	static final int SHELL_DOUBLE_COLOR = 0xDF; // 223
	static final int SHELL_HALF_DAM_COLOR	= 0x90;
	static final int SHELL_CYAN_COLOR = 0x72;
//	  ROGUE

	static final int SHELL_WHITE_COLOR = 0xD7;
	
	// ============================================================================
	// gl_local.h
	// ============================================================================
	static final String REF_VERSION = "GL 0.01";
	
	
	// ============================================================================
	// extensions (cwei) 
	// ============================================================================
	refexport_t GetRefAPI(refimport_t rimp);
	String getName();

}