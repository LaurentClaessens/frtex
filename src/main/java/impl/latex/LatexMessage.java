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

import actors.Message;

// The tag can be
// - "request". In this case, the message requests to provide the (recursive) content of the passed filename.
// - "answer". In this case, the content of the message is the (recursive) content of the filename.
public class LatexMessage implements Message
{
    private final LatexActorRef from_actor;
    private final LatexActorRef to_actor;
    private final String filename;
    private final String tag;
    private String content;

    public LatexMessage(LatexActorRef from, LatexActorRef to,String tag,String filename)
    {
        from_actor=from;
        to_actor=to;
        this.tag=tag;
        this.filename=filename;
        content="";
    }
    public LatexActorRef getSender() { return from_actor; }
    public String getTag() {return tag;} 
    public String getContent() { return content; }
    public void setContent(String content) { this.content=content; }
    public String getFilename() { return filename; }
}
