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

// Created on 09.12.2003 by RST.
// $Id: moveclip_t.java,v 1.1 2003-12-09 22:12:43 rst Exp $

package jake2.server;


import jake2.*;
import jake2.client.*;
import jake2.game.*;
import jake2.qcommon.*;
import jake2.render.*;
import jake2.server.*;

public class moveclip_t 
{
	float [] boxmins={0,0,0}, boxmaxs={0,0,0};// enclose the test object along entire move
	float	[]	mins, maxs;	// size of the moving object
	float [] mins2={0,0,0}, maxs2={0,0,0};	// size when clipping against mosnters
	float	[]	start, end;
	trace_t	trace;
	edict_t	passedict;
	int contentmask;
}