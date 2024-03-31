package com.luka5w.crackfurtjump.math;

/**
 * Utility to perform smooth jumps.
 */
public class Jump {

  private static final double G = 9.81;
  private static final double V0 = -3.5;

  private final double g;
  private int t;
  private boolean isJumping;
  private double v0;

  public Jump() {
    this(G);
  }

  public Jump(double g) {
    this.g = g;
    this.isJumping = false;
  }

  public boolean isJumping() {
    return this.isJumping;
  }

  /**
   * Starts a new jump.
   */
  public void start() {
    this.start(V0);
  }

  public void start(double v0) {
    this.resume();
    this.t = 0;
    this.v0 = v0;
  }

  /**
   * Lets the object fall down.
   */
  public void fall() {
    this.resume();
    this.t = 150;
    this.v0 = V0;
  }

  /**
   * Stops a jump.
   */
  public void stop() {
    this.pause();
    this.t = 0;
  }

  /**
   * Resumes a paused jump.
   */
  public void resume() {
    this.isJumping = true;
  }


  /**
   * Pauses a jump.
   */
  public void pause() {
    this.isJumping = false;
  }

  /**
   * Calculates the new y-position.<br/> Black magic, don't touch!
   *
   * @return the new y-Position if a jump is executed, returns 0 otherwise.
   */
  public double move() {
/*
                                                                                        .                                :,
                                                                                       :         ."!._.,__,;==<<===||>;._ 'T,
                              _wmm5mmmww,__                                            !    jwuYmwwpp5KWWpp|YTThHwww___"" '*TW_
                          _wM"            """*Kp_                                      'r  $F""""""$@$@Z$$@@@W_      "TW|"*~  T"*
                        sB"                     ]p                            _,wu^^^>uu|p]@$F"*>pjp$w>4@@@p@p$\    ,'>=,"v    '  _
                     .&F                         @_                         ^"         .aW$@$m@}@U@@pJWF"{@@@"@@Wpy_ '\L"TW$w$p_]@@$@@@p_
                    &F               w           ]@WKKKKKWWWwp__                    ;mF"T@@@@@b$@p"$@    ']R@p|F%@@@p>, "Y, "}@@$@@Q$@@@@@Iv,
                  jB       '@    $n@_@p       _                "$p                 T" .m$F|@@W@@@@u]p$y_  ] '@@$u@@@@@@@@p_YL|SI@$@@@p$@%@@p*Wbu_
                .&"         ]b   '@]@'@_.w $*                    ]b                | .@F sb"@@@@@$@F@@@_"YWwpp@@@b @@@@@@$%bw%p@$@@$|$@|I,l@@p_"T$W_
               :@            ]p  _@ " '                           $_                 @@ "|"]@@" $@@@@I@@p**^KKF@@A@@_@,$@@@@p"$U}@@@@p"@pLY@$$$W_
               j@             '"""                   _  KW,        @_               ]@@n""&@T@%_*@@@@@_]@@p   '$I@@@@@@@@n@F"@@_b$|XW'%l@@p@u"F "U
                "%p_                           _ .p  jb.w&B        )@              aB}@@@@@@]b "X<"@@@@@@@$@WwK@@@$Q@@@@@@@@_"p@@_@$$p ]@@@@@bju                 ______
                   "%WL             '@ j@!@ .p'@wW@  "F f           @            .B .$@@@@m@@ .$b,  "@@@@@@p@@@@@_F "@@@@@@@@@@@L"@@b@p@@@@@$I@"r            _&B""    '"%w_
                      '"KMMMMM@      T@K@n$_ @~]b $  `*  f         j@           j@  '$j@@F'@$u@@@    ]@h '@@@@@@@}@ '5@@@@l@@@@@@@$@@@@@@$@@@@@@I          jB"   _pppp__  "W_
                              ]       ]@'P "f"                  _&B"            "   $IF]@ |@$@Q@F   _|@@@@@B@@@@p$@   ]@@"@@@@@@@@W}$}@$@@@@@@]b@F       .@"    !@@L'}@@@   l@_
                              ]                              _&B"                  j   l@@@$@"]@@@@@@@@@bWn> "F  "Ib    $|@@$@@@@@@MB@@$b@@@@@]"b@       @        """ ]@@F   ]@
                              @                              "%W_                  |    '@@@@$,  I@@@@@@@@@pIY-         $ "@@@&@@I&B"@l@@&I@@@$ "@       @       j@@@KB"      ]_
                              ]_                     _wKMMBBBBBK@@W_                   '""]I      .r'$$&"  _sN             ]@E .&@@T '@@@@I@@@b  *       ]@      ]@           ]h
                              '@_                 _&B"                                    $      .I@@@@@W@@@@M          .  '@@ jF@$h, @@@@@]@@F        .@@WW     __          ;@
                                "TMMMNWWWWWwwpLwK"                                      .@"       "X@@@@F_            .syn 'F~ '@~'$F]@@@@@@@@             ]@   '$B         j@
                                                                                       .@"             "            _@@K"      @@u/)A@@@@@@@@               "@p            &E
                                                                                      .@"                        ./@F"        "@@UF_&@@@@@'$@,                '"NW__   _w&B
                                                                                     'Q   _,,,,                 ./"         __    }@@@@@@$                        '"T"f"
                                                                                       '*%K@L                             .@@$@@@@@@@@@@  "
                                                                                           ]$          ,                 .@F  T@"@@@@@@pr
                                                                                           $            *,               j@    "@@@@@@@@I
                                                       ___,ss>>y_ _,_                      "V.;=>>>>==,,_ $u             '"       "@@@b'$
                                                     .B!@{   """*NWL_'*u_                      .uu<w@@~^"  @              _r         $W_       __;wnYAYu_  _
                                                     @%w@Wr.      "V@p, 'Y,                  %u     '@@   jF           ;^'|N           "@ps^*""    .@@_ ,A"vIWp
                                                  sWKB**Yw,_ '-,    '"@|>,'%_                 |b     '@             _p$+   F  r       :" "$@WpIu_   ]@@@@@@MM$""v,
                                                  @bW"     '"Y., !y  jF,  T,Tp                j@      @n       _sWMF       |  r     ."   ;* "k@@@@@@p&BB""        TWp_
                                               .mW@pbTn><=,_   "p:m'XRA    'WlL               !@      @@  _,u^"    :*      !, L    ;"   r    '"T@@@F '*.FT5}b""""""*XW$
                                       ___wwp_ '@@_F """T%K@@W,_WUb         '@L                "Ywww>A@@YMbmpu__;*         'b F    \, ."         j"   ;F:&@P""W,_
                                       X@|F"f$K@@@@puu_     |FF"TF      .m"}@@@+                             }@p              |      ''          $>*"_F        'V)$@p
                                        @bI_   "@@@@@BBBBKKW$m         .@$B@@@@                        w   ;F 'Ib             T                 !   @b        .@@@@@""v_
                                         '"l@@@W_}~}@~                j@B$ @@@F                          ;B@@@  *@            !                 I  j@=       .@@@P  "v_"m
                                              "@pIw$|=               ;@b:Fmj@@=                        .$@B"&@   '$@p          y                F  !"        j@@@     '\y$
                                               '$@pl@r '*~._       _A"'@}m @@@                        ]@@@@B"      .@"@p       'T              j   |         "@@@_       "@_
                                                 ]@I,"NL    "y   .A" _wBb,&@@@                       $B@"" __       @  ""u      j              I  ,   ;         ' _,=>^*%m<l@_
                                                 'bl@w_ "Y<.__T.abs$WM$@@  j@@                      ]\@b_w*"'Tw     $u    "u    $       ___,..=@ jF  !        _>^"           @W_
                                                   "@@W$,      .@F    j@@  j]@                    _F ,W@bm    ]"    ]_"v,  ',  ;   @$B""""    !F $  !      ;^"                b",
                                                     }@L"@     &F    jF!@  jj@                   XwA}@F.B     @F    ]b   '"$"  ' _&F         _]  ,j$b@@p_p`   ______        ','m'v
                                                      j@"%@p,_|$F   ]" &$  jj@                     .A ;B     jb},   ]@      '  _s'     -~u^"""] $|"|   "]@@ ""  ./"""""Ym,    >"p_T,
                                                       @@_   "    a"I;@@b  jj@                     $m!F      "b_b   ]@"*<,   ''               j_|b !_   ]@$  r 'y/"        "\=_|I@5Iu
                                                       ]@Ty_   _wF ;@@$b   jjb                     @"j,     _@M'"%w ]@   f                     bj_ j@"  @@@ | ;F               'l@p|XW
                                                       -@$Np$X"_ sB@@ jp   jjF                    j@ Ip    j@@F    }@@|                        $p@b&bU&@@" .$F       _           "@p!'@_
                                                         "@BKM"'}m" I  @   jjF                   p@F>$]@@@p@@@     '@I*F                -~<,_ .F'$@F IZ"!  $        '_p*          "@@Iu},
                                                          'b           |,  I]F                 .F@@j6F'F"]@@@   .* '@j $       .       !      &jF['@p@@ ' :F       .@@@"         *,Tl@@u$
                                                           $            r  |]F                .F;@|F$ `$Wm T    !   @|F]F      :       ''.   I"$>$ '@@@_' I         '')p_         'b,]$ 'F
                                                           j            jr []                 jhj@ j@._]@$      j  .F'Fj@      '           'X I|r]p '@@@'|F .mF$     ]@@@_         'b '[ ]
                                                           !             I_[$               s@@5@FF @"$@UF      j  !F b'$u      \          m j"rF!F%_ @$F@ ;" ;F     _]@@@        *,", |,j,
                                                           !,            "bF|              j@$b@@ |U@T@ @F      j  j| $ $k       5       .B jF +F'F Ty ]@$ k ,   _j@@@@@@@+        'r"  I!F
                                                           'F             ]bj_           .@@@@B"T, $@F@_]F     _&p_$ |] ]'%_     "u       |jF ! F F   Tu"@_$F, j@@@@"  __pwp_       'v  !Ub
                                                           'F             !@@"          j@@@F     "@@$]b] .&@@@@@"\$ 'I_"n }u     ]      :$"  [ 5 $     "']@@r @@@" .]@@@BBf@@@     ''V  I$
                                                           .F             !'@        ;m@}@U$F      j@}p@$ @W@@" ] :h  ]p }_  }u   |$    .@   :L | I      _@I@F @@@    "      ]@h     |'m !r
                                                           .F             | ]p    .m"a@ 'F@F _@@@@@@@@@$@ ]@@F  ] j=   @  },   T*      JF    I" | ]   s@@B@"@F ]@@@L_    _p@@@b       r"r $
                                                           .F             | $]_   .A}F  ''L@@@bB""  '"@pb '@@@_ I ]    $b  '%@WwwwmHM""     !F  | j .@@"  ]jIb  '$@@@@@@@B""          '|FjI_
                                                           .F             | ] b  p@JF   :@|@@"   ;@W  j@b+ '@@@@@F@    ]"F    "y :_        .N$  | !F@@$p  '@@@                         j_j>b
                                                           .F               I '$@@|FK    l@@@_ _@@@F  @@b       F:b    !F|,     T^m*.      mj$  |  F"@@@@@@@@@                         !@$ },
                                                           .F               I  "v!F"F      l@@@BB"   ]@]W       $}F     b ]_        | '**Ib F"  |  $       "Y@                         .@@  F
                                                           'F               I   !_]_]_  U    '"" _w&@@@@F       N@      $  $        '**"    '   I  $      a$]b              __LL___    .b@
                                                           'F               I   !  k_}, |       '@@@B" @        $@      j  'p                   j  j     a@@$b           ;@@@@@@@@@@p   b@,j
                                                           'F               $   $   "yI,         @@@n .@         @      'b  ]_                  j  j=   $@@"@"          '@@@@_   "@@@   @Fb!p
                                                           'F               $  ;b     "$b        )@@@ !@         @p      $   $                  j  'F  j@$F @             "$@@@@  ]@@   ] $ $
                                                            v_     ,^*V     $  &         "W_ r  sF)@WW]F        '@$      j   'p                 j   $      .@_                    ]@@  jb ] jn
                                                             'I, ;"         $ `b           "@@@b;w"$B"]        .|h!F     !F   ]_                j   ]      T@@              j@@@@@B"   jF !h $
                                                               "@_  ./      $  '         ,W@B"T@m@$W"_@        @jF $      $    $                j   j_      @@@@_           @@B        $b  b )F
                                                                 $_A"       $         JM@M"         ""@       :@]  j,     ]    'p               j   'F     .l@@@@%p         ""        :b@  b  @
                                                                  b .       $      JK@m"         "n  .@       'b]   b     'p    |u              |sF  $     !r^@]@ ]        $@@        ] @  b  Tb
                                                                  'm '*~,   $;mK"""""             'N_:b         $   ]      $     "p            .@F   $     .F.&@@&"                   ] @  $
                                                                   ],      m@"                     _"j@_@_      $   'b     ],                _/F@    j'+   .F!$@p_                   `l@b _$
                                                                     '""" ' $                      " ]bI@@_     $    $      b             ,^"  lF  ' !uF@w :F    "YQ*<=;_              jW""
                                                                            $                        ] .I@b     @    },     ]          .wF>^*"  '    |b$]$@ "Yu_    '*v,"              $
                                                                            $                        ]  '$@     @     @_    ]n                        $"u$I@   '*v_                    $
 */
    if (this.isJumping) {
      this.t++;
      return this.v0 + this.g * t / 200;
    }
    return 0;
  }
}
