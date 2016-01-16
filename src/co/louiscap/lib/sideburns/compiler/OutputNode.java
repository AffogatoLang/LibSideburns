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

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Louis
 */
public abstract class OutputNode {
    
    protected String id, value;
    protected OutputNode[] nodes;
    
    /**
     * Create a new OutputNode with the specified ID, the given value it
     * represents and (optionally) a list of nodes other nodes it contains
     * @param id The unique identifier for this OutputNode
     * @param value The value this node represents
     * @param nodes Other nodes that this OutputNode might contain
     */
    public OutputNode (String id, String value, OutputNode... nodes) {
        this.id = id;
        this.value = value;
        this.nodes = nodes;
    }
    
    /**
     * Convert the contents of this output node to as a string
     * @return The String representation of this OutputNode
     */
    @Override
    public abstract String toString();

    /**
     * {@inheritDoc }
     * <br><br>Created using Netbeans default implementation with (Insert Code...)
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.value);
        hash = 19 * hash + Arrays.deepHashCode(this.nodes);
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
        final OutputNode other = (OutputNode) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Arrays.deepEquals(this.nodes, other.nodes)) {
            return false;
        }
        return true;
    }
    
    
}
