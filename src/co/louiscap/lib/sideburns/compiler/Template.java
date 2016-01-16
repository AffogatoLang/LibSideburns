/*
Copyright (c) 2015, Louis Capitanchik
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of Affogato nor the names of its associated properties or
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package co.louiscap.lib.sideburns.compiler;

import co.louiscap.lib.compat.json.JsonObject;
import co.louiscap.lib.sideburns.Sideburns;
import java.util.List;
import java.util.Objects;

/**
 * A compiled Sideburns template that can be rendered with data to create the
 * expected output
 * @author Louis Capitanchik
 */
public class Template {
    /**
     * The context under which this instance was created, allowing it to access
     * various environment properties regardless of where it is passed
     */
    protected Sideburns context;
    
    protected List<OutputNode> nodes;
    
    public Template(Sideburns context, List<OutputNode> nodes) {
        this.context = context;
        this.nodes = nodes;
    }

    /**
     * {@inheritDoc }
     * <br><br>Created using Netbeans default implementation with (Insert Code...)
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.context);
        hash = 59 * hash + Objects.hashCode(this.nodes);
        return hash;
    }

    /**
     * {@inheritDoc }
     * <br><br>Created using Netbeans default implementation with (Insert Code...)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Template other = (Template) obj;
        if (Objects.equals(this.context, other.context)
                && Objects.equals(this.nodes, other.nodes)) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    public String render(JsonObject data) {
        return render(data, new JsonObject());
    }
    
    public String render(JsonObject data, JsonObject opts){
        return null;
    }
}
