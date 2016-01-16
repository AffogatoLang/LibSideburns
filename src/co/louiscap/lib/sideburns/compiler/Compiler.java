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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Louis Capitanchik
 */
public class Compiler {
    
    public static final int STANDARD_TAG =  0b00000000;
    public static final int CLOSING_TAG =   0b00000001;
    public static final int DIRECTIVE_TAG = 0b00000010;
    public static final int BLOCK_TAG =     0b00000100;
    public static final int ARRAY_TAG =     0b00001000;
    public static final int INCLUDE_TAG =   0b00010000;
    
    public static final int HAS_ESCAPE =    0b10000000;
    
    /**
     * Using this Pattern<br>
     * This regular expression will match and split all valid tags for Sideburns, and provides
     * all data needed to appropriately lex each tag<br>
     *<br>
     * Result indexes (from re.exec()):<br>
     * [0] The whole tag that has been matched<br>
     * [1, 9] These are the opening and closing double square brackets, respectively. Always present
     * [2] The Tag modifier. This will either be '/' for a closing tag, '#' for a pre-process directive
     *     or undefined for all other tags. Processing should branch here depending on whether or not this
     *     token is a hash ('#'). Indexes for one branch will be undefined in case of the other branch.<br>
     *<br>
     * == In case of Directive ==<br>
     * [3] The identifier of the directive to set. Always present<br>
     * [4] The value to set the directive. Always present<br>
     *<br>
     * == In case of other tag ==<br>
     * [5] The Block modifier. Will be '*' for arrays, '&' for context and undefined for simple tags<br>
     * [6] The Data modifier. Will be '!' for an escape with the global set, '!([7])' for an escape with a<br>
     *     specified set or undefined for unescaped data<br>
     * [7] If a set has been specified, this will be the name of the set on its own, seperate from the data<br>
     *     modifier. It will otherwise be undefined<br>
     * [8] The identifier for the data or block that the tag represents. Always present<br>
     * 
     * For test: <a href="http://fiddle.re/4pmfk6">http://fiddle.re/4pmfk6</a>
     **/
    public static final Pattern SBPATTERN = Pattern.compile("(\\[\\[)(\\#|\\/)?\\s*(?:([a-zA-Z]+[a-zA-Z0-9]*)\\s*\\:\\s*([a-zA-Z]+[a-zA-Z0-9]*)|([\\*\\&\\>\\?]?)\\s*((?:\\!(?:\\(([a-zA-Z]+[a-zA-Z0-9]*)\\))?)?)\\s*(?<IDENT>[a-zA-Z](?:[a-zA-Z0-9]*(?:\\.(?=[a-zA-Z]))?)+))\\s*(\\]\\])");
    public static final Pattern WHITESPACE = Pattern.compile("^\\s*$");
    
    private Compiler(){}
    
    public Template compile(String src) {
        return null;
    }
    
    List<Token> tokenise(String src) throws UnsupportedSyntaxException {
        ArrayList<Token> tokens = new ArrayList<>();
        Matcher matcher = SBPATTERN.matcher(src);
        int lastPos = 0, matchStart, matchEnd, tagType;
        String curGroup, ident;
        JsonObject tagOpts;
        JsonValue tagValue;
        
        while(matcher.find()) {
            matchStart = matcher.start();
            matchEnd = matcher.end();
            tagType = STANDARD_TAG;
            tagOpts = createDefaultOpts();
            
            if(matchStart != lastPos) {
                tokens.add(createStringToken(src, lastPos, matchStart));
                lastPos = matchStart;
            }
            
            curGroup = matcher.group(2);
            if(curGroup != null){
                switch (curGroup) {
                    case "/":
                        tagType = tagType | CLOSING_TAG;
                        break;
                    case "#":
                        tagType = tagType | DIRECTIVE_TAG;
                        break;
                    default:
                        throw new UnsupportedSyntaxException("tag modifier " + curGroup, "Lexer");
                }
            }
            
            curGroup = matcher.group(5);
            if(curGroup != null) {
                switch(curGroup) {
                    case "*":
                        tagType = tagType | ARRAY_TAG;
                        break;
                    case "&":
                        tagType = tagType | BLOCK_TAG;
                        break;
                    case ">":
                        tagType = tagType | INCLUDE_TAG;
                        break;
                    case "?":
                        throw new UnsupportedSyntaxException("tag type QUERY", "Lexer");
                }
            }
            
            curGroup = matcher.group(6);
            if(curGroup != null) {
                switch(curGroup) {
                    case "!":
                        tagType = tagType | HAS_ESCAPE;
                        break;
                    default:
                        // Room to expand
                        break;
                }
            }
            
            if((tagType & DIRECTIVE_TAG) == DIRECTIVE_TAG) {
                ident = "T_DIRECTIVE";
                JsonObject val = new JsonObject();
                val.put("key", new JsonString(matcher.group(3)));
                val.put("value", new JsonString(matcher.group(4)));
                tagValue = val;
            } else {
                if((tagType & CLOSING_TAG) == CLOSING_TAG) {
                    tagOpts.put("close", JsonValue.TRUE);
                }
                if((tagType & BLOCK_TAG) == BLOCK_TAG) {
                    ident = "T_BLOCK";
                } else if((tagType & ARRAY_TAG) == ARRAY_TAG) {
                    ident = "T_LOOP";
                } else if((tagType & INCLUDE_TAG) == INCLUDE_TAG) {
                    ident = "T_IMPORT";
                } else {
                    ident = "T_DATA";
                }
                
                tagValue = new JsonString(matcher.group("IDENT"));
            }
            
            Token tok = new Token(ident, tagValue, tagOpts);
            tokens.add(tok);
            
            lastPos = matchEnd;
        }
        
        if(lastPos != src.length()) {
            tokens.add((createStringToken(src, lastPos, src.length())));
        }
        
        return tokens;
    }
    
    private JsonObject createDefaultOpts () {
        JsonObject opts = new JsonObject();
        opts.put("close", JsonValue.FALSE);
        opts.put("escape", JsonValue.FALSE);
        opts.put("escapeType", JsonValue.NULL);
        return opts;
    }
    
    private Token createStringToken (String src, int start, int end) {
        String content = src.substring(start, end);
        return new Token("STRING", new JsonString(content));
    }
    
    public static class SideburnsCompiler {
        public static final Compiler RUN = new Compiler();
    }
}
