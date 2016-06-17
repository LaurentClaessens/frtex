/*
Copyright 2016 Laurent Claessens
contact : moky.math@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/

package actors.impl.latex;

import java.io.File;

import actors.Message;

public class LatexMessage implements Message
{
    private final LatexActorRef from_actor;
    private final LatexActorRef to_actor;
    private final File filepath;

    public LatexMessage(LatexActorRef from, LatexActorRef to,File filepath)
    {
        from_actor=from;
        to_actor=to;
        this.filepath=filepath;
    }
    public LatexActorRef getSender() { return from_actor; }
    public File getFilepath() { return filepath; }
}
