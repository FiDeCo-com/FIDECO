<nav
			class="navbar navbar-expand-lg navbar-light bg-light bottom-navbar">
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mx-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> 카테고리 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#">사무용/가정용</a> 
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">고성능/전문가용</a> 
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">3D게임/그래픽용</a>
							
							
						</div></li>
					<li class="nav-item"><a class="nav-link" href="#">상품</a></li>
					<li class="nav-item"><a class="nav-link" href="#">고객센터</a></li>
					<li class="nav-item"><a class="nav-link"
						th:href="@{/PaymentInsert}">결제</a></li>
					<li class="nav-item"><a class="nav-link"
						th:href="@{/NoticeSelect}">공지사항</a></li>
					<li class="nav-item"><a class="nav-link" th:href="@{/track}">배송조회</a>
					</li>
				</ul>
			</div>
		</nav>