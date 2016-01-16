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
import co.louiscap.lib.compat.json.JsonString;
import co.louiscap.lib.compat.json.JsonValue;
import co.louiscap.lib.sideburns.compiler.Compiler.SideburnsCompiler;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Louis Capitanchik &lt;contact@louiscap.co&gt;
 */
public class CompilerTest {
    
    public CompilerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of compile method, of class Compiler.
     */
//    @Test
//    public void testCompile() {
//        System.out.println("compile");
//        Compiler instance = SideburnsCompiler.RUN;
//        Template expResult = null;
//        Template result = instance.compile(src);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of tokenise method, of class Compiler.
     * @throws java.lang.Exception
     */
    @Test
    public void testTokeniseSimpleData() throws Exception {
        System.out.println("tokenise");
        
        List<Token> expResult = new ArrayList<>();
        Compiler instance = SideburnsCompiler.RUN;
        
        String src = "Hello there [[name]], good to see you";
        
        Token tk = new Token("STRING", new JsonString("Hello there "));
        expResult.add(tk);
        tk = new Token("T_DATA", new JsonString("name"), createDefaultOpts());
        expResult.add(tk);
        tk = new Token("STRING", new JsonString(", good to see you"));
        expResult.add(tk);
        
        List<Token> result = instance.tokenise(src);
        assertEquals(expResult, result);
    }
    
    
    private JsonObject createDefaultOpts () {
        JsonObject opts = new JsonObject();
        opts.put("close", JsonValue.FALSE);
        opts.put("escape", JsonValue.FALSE);
        opts.put("escapeType", JsonValue.NULL);
        return opts;
    }
    
}
