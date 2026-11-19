/**
 * TİVİM - Ana Uygulama
 * Kanal yönetimi, UI kontrolü, admin panel
 */
const App = {
    channels: [],
    filteredChannels: [],
    categories: [],
    currentCategory: 'all',
    currentChannelIndex: -1,
    isAdminOpen: false,
    adminAuthenticated: false,

    /**
     * Uygulama başlat
     */
    async init() {
        console.log('🚀 TİVİM başlatılıyor...');

        this.updateSplash('Sistem başlatılıyor...');

        // Player başlat
        Player.init();

        // Kumanda başlat
        Remote.init();

        // Saat başlat
        this.startClock();

        // Kanalları yükle
        this.updateSplash('Kanallar yükleniyor...');
        await this.loadChannels();

        // Event listener'lar
        this.bindEvents();

        // Splash'ı kapat
        setTimeout(() => {
            document.getElementById('splashScreen').style.opacity = '0';
            setTimeout(() => {
                document.getElementById('splashScreen').classList.add('hidden');
                document.getElementById('app').classList.remove('hidden');

                // İlk kanala fokuslan
                setTimeout(() => {
                    const firstCard = document.querySelector('.channel-card');
                    if (firstCard) Remote.focusChannelCard(firstCard);
                }, 300);
            }, 500);
        }, 1500);

        // Tam ekran yap (Android Box için)
        this.requestFullscreen();

        console.log('✅ TİVİM hazır!');
    },

    /**
     * Kanalları yükle
     */
    async loadChannels() {
        const settings = Storage.getSettings();
        const cache = Storage.getCachedChannels();

        // Cache kontrolü
        if (cache && cache.data && (Date.now() - cache.timestamp < settings.updateInterval)) {
            console.log('📦 Cache\'den yükleniyor...');
            this.channels = cache.data;
            this.updateSplash('Cache\'den yüklendi');
        } else {
            try {
                this.updateSplash('M3U indiriliyor...');
                this.channels = await M3UParser.fetchAndParse(settings.m3uUrl);

                // Gizli kanalları filtrele
                this.channels = this.channels.filter(ch => !Storage.isChannelHidden(ch.id));

                // Numaraları güncelle
                this.channels.forEach((ch, i) => ch.number = i + 1);

                // Cache'e kaydet
                Storage.setCachedChannels(this.channels);
                Storage.updateSettings({ lastUpdate: Date.now() });

                this.updateSplash(`${this.channels.length} kanal yüklendi`);
            } catch (error) {
                console.error('Kanal yükleme hatası:', error);
                this.updateSplash('Hata! Cache kontrol ediliyor...');

                // Cache varsa onu kullan
                if (cache && cache.data) {
                    this.channels = cache.data;
                    this.updateSplash('Eski cache kullanılıyor');
                } else {
                    this.updateSplash('Kanal yüklenemedi!');
                    this.channels = [];
                }
            }
        }

        // Kategorileri çıkar
        this.categories = M3UParser.extractCategories(this.channels);

        // UI oluştur
        this.renderCategories();
        this.filterByCategory('all');
        this.updateChannelCount();
    },

    /**
     * Kategorileri render et
     */
    renderCategories() {
        const scroll = document.getElementById('categoryScroll');

        // Sabit butonları koru
        const existingButtons = scroll.querySelectorAll('.category-btn[data-category="all"], .category-btn[data-category="favorites"], .category-btn[data-category="recent"]');

        // Dinamik butonları temizle
        scroll.querySelectorAll('.category-btn:not([data-category="all"]):not([data-category="favorites"]):not([data-category="recent"])').forEach(b => b.remove());

        // Kategorileri ekle
        this.categories.forEach(cat => {
            const btn = document.createElement('button');
            btn.className = 'category-btn';
            btn.dataset.category = cat.name;
            btn.dataset.focusable = 'true';
            btn.dataset.focusGroup = 'categories';
            btn.innerHTML = `
                <i class="fas ${cat.icon}"></i>
                <span>${cat.name} (${cat.count})</span>
            `;
            scroll.appendChild(btn);
        });
    },

    /**
     * Kategoriye göre filtrele
     */
    filterByCategory(category) {
        this.currentCategory = category;

        switch (category) {
            case 'all':
                this.filteredChannels = [...this.channels];
                break;
            case 'favorites':
                const favIds = Storage.getFavorites();
                this.filteredChannels = this.channels.filter(ch => favIds.includes(ch.id));
                break;
            case 'recent':
                const recentIds = Storage.getRecent();
                this.filteredChannels = recentIds
                    .map(id => this.channels.find(ch => ch.id === id))
                    .filter(ch => ch);
                break;
            default:
                this.filteredChannels = this.channels.filter(ch => ch.group === category);
                break;
        }

        this.renderChannels();
        this.updateCategoryButtons();
    },

    /**
     * Kanalları render et
     */
    renderChannels() {
        const grid = document.getElementById('channelGrid');
        const noResults = document.getElementById('noResults');

        if (this.filteredChannels.length === 0) {
            grid.innerHTML = '';
            noResults.classList.remove('hidden');
            return;
        }

        noResults.classList.add('hidden');

        // Performans: DocumentFragment kullan
        const fragment = document.createDocumentFragment();

        this.filteredChannels.forEach((channel, index) => {
            const card = document.createElement('div');
            card.className = 'channel-card';
            card.dataset.channelId = channel.id;
            card.dataset.focusable = 'true';
            card.dataset.focusGroup = 'channels';

            if (Storage.isFavorite(channel.id)) {
                card.classList.add('is-favorite');
            }

            const logoHTML = channel.logo
                ? `<img class="channel-logo" src="${channel.logo}" alt="${channel.name}" loading="lazy" onerror="this.style.display='none';this.nextElementSibling.style.display='flex'">`
                : '';

            const placeholderHTML = `<div class="channel-logo-placeholder" style="${channel.logo ? 'display:none' : ''}">${channel.name.charAt(0).toUpperCase()}</div>`;

            card.innerHTML = `
                <span class="channel-number">${channel.number}</span>
                <span class="fav-indicator"><i class="fas fa-star"></i></span>
                ${logoHTML}
                ${placeholderHTML}
                <div class="channel-name">${channel.name}</div>
                <span class="channel-category-tag">${channel.group}</span>
            `;

            // Click event
            card.addEventListener('click', () => {
                this.playChannel(channel.id);
            });

            fragment.appendChild(card);
        });

        grid.innerHTML = '';
        grid.appendChild(fragment);
    },

    /**
     * Kategori butonlarını güncelle
     */
    updateCategoryButtons() {
        document.querySelectorAll('.category-btn').forEach(btn => {
            btn.classList.remove('active');
            if (btn.dataset.category === this.currentCategory) {
                btn.classList.add('active');
            }
        });
    },

    /**
     * Kanal ara
     */
    performSearch(query) {
        query = query.toLowerCase().trim();

        if (!query) {
            this.filterByCategory(this.currentCategory);
            return;
        }

        this.filteredChannels = this.channels.filter(ch =>
            ch.name.toLowerCase().includes(query) ||
            ch.group.toLowerCase().includes(query) ||
            ch.number.toString().includes(query)
        );

        this.renderChannels();
    },

    /**
     * Kanal oynat
     */
    async playChannel(channelId) {
        const channel = this.channels.find(ch => ch.id === channelId);
        if (!channel) {
            console.error('Kanal bulunamadı:', channelId);
            return;
        }

        // Mevcut kanal indexini kaydet
        this.currentChannelIndex = this.filteredChannels.findIndex(ch => ch.id === channelId);

        // Player overlay'i göster
        document.getElementById('playerOverlay').classList.remove('hidden');

        // Tam ekran
        this.requestFullscreen();

        // Oynat
        await Player.play(channel);

        // İstatistik güncelle
        Storage.updateWatchTime(channelId, 1);
    },

    /**
     * Sonraki kanal
     */
    nextChannel() {
        if (this.filteredChannels.length === 0) return;

        this.currentChannelIndex = (this.currentChannelIndex + 1) % this.filteredChannels.length;
        const channel = this.filteredChannels[this.currentChannelIndex];
        this.playChannel(channel.id);

        Player.showOSD(`${channel.number}. ${channel.name}`);
    },

    /**
     * Önceki kanal
     */
    prevChannel() {
        if (this.filteredChannels.length === 0) return;

        this.currentChannelIndex = (this.currentChannelIndex - 1 + this.filteredChannels.length) % this.filteredChannels.length;
        const channel = this.filteredChannels[this.currentChannelIndex];
        this.playChannel(channel.id);

        Player.showOSD(`${channel.number}. ${channel.name}`);
    },

    /**
     * Numaraya göre kanal bul
     */
    getChannelByNumber(number) {
        return this.channels.find(ch => ch.number === number) || null;
    },

    /**
     * Player'ı kapat
     */
    closePlayer() {
        Player.stop();
        document.getElementById('playerOverlay').classList.add('hidden');
        document.getElementById('miniChannelList').classList.add('hidden');

        // Kanal kartına fokuslan
        const card = document.querySelector(`.channel-card[data-channel-id="${Player.currentChannel?.id}"]`);
        if (card) {
            Remote.focusChannelCard(card);
        }
    },

    /**
     * Mini kanal listesi toggle
     */
    toggleMiniList() {
        const miniList = document.getElementById('miniChannelList');
        const isVisible = !miniList.classList.contains('hidden');

        if (isVisible) {
            miniList.classList.add('hidden');
        } else {
            this.renderMiniList();
            miniList.classList.remove('hidden');

            // Aktif kanala fokuslan
            setTimeout(() => {
                const active = miniList.querySelector('.mini-channel-item.active');
                if (active) {
                    active.classList.add('focused');
                    active.scrollIntoView({ block: 'center' });
                }
            }, 100);
        }
    },

    /**
     * Mini kanal listesini render et
     */
    renderMiniList() {
        const content = document.getElementById('miniListContent');
        const currentId = Player.currentChannel?.id;

        content.innerHTML = this.filteredChannels.map(ch => `
            <div class="mini-channel-item ${ch.id === currentId ? 'active' : ''}" 
                 data-channel-id="${ch.id}" 
                 onclick="App.playChannel('${ch.id}'); App.toggleMiniList();">
                <span class="mini-ch-num">${ch.number}</span>
                ${ch.logo
                    ? `<img src="${ch.logo}" alt="${ch.name}" onerror="this.style.display='none'">`
                    : `<div style="width:36px;height:36px;border-radius:6px;background:var(--gradient-1);display:flex;align-items:center;justify-content:center;font-weight:700;font-size:14px;">${ch.name.charAt(0)}</div>`
                }
                <span class="mini-ch-name">${ch.name}</span>
            </div>
        `).join('');
    },

    /**
     * Favori toggle
     */
    toggleCurrentFavorite() {
        if (this.isPlayerVisible() && Player.currentChannel) {
            const added = Storage.toggleFavorite(Player.currentChannel.id);
            Player.updateFavoriteButton(Player.currentChannel.id);
            Player.showOSD(added ? '⭐ Favorilere eklendi' : '☆ Favorilerden çıkarıldı');
        } else {
            // Fokuslu kanal
            const focused = document.querySelector('.channel-card.focused');
            if (focused) {
                const channelId = focused.dataset.channelId;
                const added = Storage.toggleFavorite(channelId);
                focused.classList.toggle('is-favorite', added);
            }
        }
    },

    /**
     * Arama kutusuna fokuslan
     */
    focusSearch() {
        const input = document.getElementById('searchInput');
        input.focus();
        document.getElementById('searchBox').classList.add('focused');
    },

    // ======== Admin Panel ========

    toggleAdmin() {
        if (this.isAdminOpen) {
            this.closeAdmin();
        } else {
            this.openAdmin();
        }
    },

    openAdmin() {
        // Şifre kontrolü
        if (!this.adminAuthenticated) {
            const password = prompt('Admin Şifresi:');
            const settings = Storage.getSettings();
            if (password !== settings.password) {
                alert('Yanlış şifre!');
                return;
            }
            this.adminAuthenticated = true;
        }

        this.isAdminOpen = true;
        this.renderAdminPanel();
    },

    closeAdmin() {
        this.isAdminOpen = false;
        const overlay = document.querySelector('.admin-overlay');
        if (overlay) overlay.remove();
    },

    renderAdminPanel() {
        // Varsa kaldır
        const existing = document.querySelector('.admin-overlay');
        if (existing) existing.remove();

        const settings = Storage.getSettings();
        const stats = Storage.getStats();

        const overlay = document.createElement('div');
        overlay.className = 'admin-overlay';

        overlay.innerHTML = `
            <div class="admin-container">
                <div class="admin-header">
                    <h1><i class="fas fa-cog"></i> Admin Paneli</h1>
                    <button class="admin-close" data-focusable="true" onclick="App.closeAdmin()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="admin-body">
                    <div class="admin-sidebar">
                        <button class="admin-nav-btn active" data-section="dashboard" data-focusable="true" onclick="App.switchAdminSection('dashboard')">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </button>
                        <button class="admin-nav-btn" data-section="channels" data-focusable="true" onclick="App.switchAdminSection('channels')">
                            <i class="fas fa-tv"></i> Kanallar
                        </button>
                        <button class="admin-nav-btn" data-section="playlist" data-focusable="true" onclick="App.switchAdminSection('playlist')">
                            <i class="fas fa-list"></i> Playlist
                        </button>
                        <button class="admin-nav-btn" data-section="settings" data-focusable="true" onclick="App.switchAdminSection('settings')">
                            <i class="fas fa-sliders-h"></i> Ayarlar
                        </button>
                        <button class="admin-nav-btn" data-section="backup" data-focusable="true" onclick="App.switchAdminSection('backup')">
                            <i class="fas fa-database"></i> Yedekleme
                        </button>
                    </div>
                    <div class="admin-content" id="adminContent">
                        <!-- Dashboard -->
                        <div class="admin-section active" id="section-dashboard">
                            <h2><i class="fas fa-tachometer-alt"></i> Dashboard</h2>
                            <div class="stats-grid">
                                <div class="stat-card">
                                    <div class="stat-icon blue"><i class="fas fa-tv"></i></div>
                                    <div class="stat-value">${this.channels.length}</div>
                                    <div class="stat-label">Toplam Kanal</div>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-icon green"><i class="fas fa-layer-group"></i></div>
                                    <div class="stat-value">${this.categories.length}</div>
                                    <div class="stat-label">Kategori</div>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-icon yellow"><i class="fas fa-star"></i></div>
                                    <div class="stat-value">${Storage.getFavorites().length}</div>
                                    <div class="stat-label">Favori</div>
                                </div>
                                <div class="stat-card">
                                    <div class="stat-icon purple"><i class="fas fa-clock"></i></div>
                                    <div class="stat-value">${Math.round(stats.totalWatchTime / 60)}dk</div>
                                    <div class="stat-label">İzleme Süresi</div>
                                </div>
                            </div>
                            <h3>Kategoriler</h3>
                            <div style="margin-top:12px;">
                                ${this.categories.map(cat => `
                                    <div style="display:flex;justify-content:space-between;align-items:center;padding:8px 16px;border-bottom:1px solid var(--border);">
                                        <span><i class="fas ${cat.icon}" style="width:20px;margin-right:8px;color:var(--accent)"></i>${cat.name}</span>
                                        <span style="color:var(--text-muted)">${cat.count} kanal</span>
                                    </div>
                                `).join('')}
                            </div>
                        </div>

                        <!-- Kanallar -->
                        <div class="admin-section" id="section-channels">
                            <h2><i class="fas fa-tv"></i> Kanal Yönetimi</h2>
                            <div style="margin-bottom:16px;">
                                <input type="text" id="adminChannelSearch" placeholder="Kanal ara..." 
                                    style="padding:10px 16px;background:var(--bg-card);border:1px solid var(--border);border-radius:8px;color:white;width:100%;font-size:14px;"
                                    oninput="App.filterAdminChannels(this.value)">
                            </div>
                            <div style="max-height:500px;overflow-y:auto;" id="adminChannelList">
                                <table class="channel-table">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Logo</th>
                                            <th>Kanal Adı</th>
                                            <th>Kategori</th>
                                            <th>Tür</th>
                                            <th>Görünür</th>
                                        </tr>
                                    </thead>
                                    <tbody id="adminChannelTableBody">
                                        ${this.channels.slice(0, 100).map(ch => `
                                            <tr data-id="${ch.id}">
                                                <td>${ch.number}</td>
                                                <td>${ch.logo ? `<img class="ch-logo" src="${ch.logo}" onerror="this.style.display='none'">` : '-'}</td>
                                                <td>${ch.name}</td>
                                                <td>${ch.group}</td>
                                                <td><span style="background:var(--bg-card);padding:2px 8px;border-radius:4px;font-size:11px;">${ch.type.toUpperCase()}</span></td>
                                                <td>
                                                    <div class="toggle-switch ${Storage.isChannelHidden(ch.id) ? '' : 'active'}" 
                                                         onclick="App.toggleChannelVisibility('${ch.id}', this)"></div>
                                                </td>
                                            </tr>
                                        `).join('')}
                                    </tbody>
                                </table>
                                ${this.channels.length > 100 ? '<p style="padding:16px;color:var(--text-muted);text-align:center;">İlk 100 kanal gösteriliyor. Arama yaparak diğerlerini bulabilirsiniz.</p>' : ''}
                            </div>
                        </div>

                        <!-- Playlist -->
                        <div class="admin-section" id="section-playlist">
                            <h2><i class="fas fa-list"></i> Playlist Yönetimi</h2>
                            <div class="form-group">
                                <label>M3U Playlist URL</label>
                                <input type="url" id="playlistUrl" value="${settings.m3uUrl}" placeholder="https://example.com/playlist.m3u">
                            </div>
                            <div class="form-group">
                                <label>Güncelleme Aralığı</label>
                                <select id="updateInterval">
                                    <option value="1800000" ${settings.updateInterval === 1800000 ? 'selected' : ''}>30 Dakika</option>
                                    <option value="3600000" ${settings.updateInterval === 3600000 ? 'selected' : ''}>1 Saat</option>
                                    <option value="21600000" ${settings.updateInterval === 21600000 ? 'selected' : ''}>6 Saat</option>
                                    <option value="86400000" ${settings.updateInterval === 86400000 ? 'selected' : ''}>24 Saat</option>
                                </select>
                            </div>
                            <div style="display:flex;gap:12px;">
                                <button class="btn btn-primary" onclick="App.savePlaylistSettings()">
                                    <i class="fas fa-save"></i> Kaydet
                                </button>
                                <button class="btn btn-success" onclick="App.refreshPlaylist()">
                                    <i class="fas fa-sync-alt"></i> Şimdi Güncelle
                                </button>
                            </div>
                            <div class="admin-alert info" style="margin-top:16px;">
                                <i class="fas fa-info-circle"></i>
                                Son güncelleme: ${settings.lastUpdate ? new Date(settings.lastUpdate).toLocaleString('tr-TR') : 'Hiç güncellenmedi'}
                            </div>

                            <h3 style="margin-top:24px;">Manuel Kanal Ekle</h3>
                            <div class="form-group">
                                <label>Kanal Adı</label>
                                <input type="text" id="manualChName" placeholder="Kanal adı">
                            </div>
                            <div class="form-group">
                                <label>Stream URL</label>
                                <input type="url" id="manualChUrl" placeholder="http://...">
                            </div>
                            <div class="form-group">
                                <label>Kategori</label>
                                <input type="text" id="manualChGroup" placeholder="Kategori" value="Özel">
                            </div>
                            <div class="form-group">
                                <label>Logo URL (opsiyonel)</label>
                                <input type="url" id="manualChLogo" placeholder="https://...">
                            </div>
                            <button class="btn btn-primary" onclick="App.addManualChannel()">
                                <i class="fas fa-plus"></i> Kanal Ekle
                            </button>
                        </div>

                        <!-- Ayarlar -->
                        <div class="admin-section" id="section-settings">
                            <h2><i class="fas fa-sliders-h"></i> Ayarlar</h2>
                            <div class="form-group">
                                <label>Admin Şifresi</label>
                                <input type="password" id="adminPassword" value="${settings.password}" placeholder="Yeni şifre">
                            </div>
                            <div class="form-group">
                                <label>Varsayılan Ses Seviyesi</label>
                                <input type="range" id="defaultVolume" min="0" max="100" value="${settings.volume}"
                                    style="width:100%;">
                                <span id="volumeValue">${settings.volume}%</span>
                            </div>
                            <div class="form-group">
                                <label>Otomatik Oynatma</label>
                                <div class="toggle-switch ${settings.autoplay ? 'active' : ''}" id="autoplayToggle"
                                     onclick="this.classList.toggle('active')"></div>
                            </div>
                            <div class="form-group">
                                <label>Kanal Numaralarını Göster</label>
                                <div class="toggle-switch ${settings.showChannelNumbers ? 'active' : ''}" id="showNumbersToggle"
                                     onclick="this.classList.toggle('active')"></div>
                            </div>
                            <button class="btn btn-primary" onclick="App.saveSettings()">
                                <i class="fas fa-save"></i> Ayarları Kaydet
                            </button>
                        </div>

                        <!-- Yedekleme -->
                        <div class="admin-section" id="section-backup">
                            <h2><i class="fas fa-database"></i> Yedekleme</h2>
                            <div style="display:flex;gap:12px;margin-bottom:24px;">
                                <button class="btn btn-primary" onclick="App.exportData()">
                                    <i class="fas fa-download"></i> Dışa Aktar
                                </button>
                                <button class="btn btn-success" onclick="App.importData()">
                                    <i class="fas fa-upload"></i> İçe Aktar
                                </button>
                                <button class="btn btn-danger" onclick="App.resetAll()">
                                    <i class="fas fa-trash"></i> Tümünü Sıfırla
                                </button>
                            </div>
                            <div class="form-group">
                                <label>İçe Aktar (JSON yapıştırın)</label>
                                <textarea id="importDataArea" placeholder='{"favorites": [...], "settings": {...}}'></textarea>
                            </div>
                            <button class="btn btn-primary" onclick="App.importFromTextarea()">
                                <i class="fas fa-file-import"></i> JSON'dan İçe Aktar
                            </button>

                            <div class="admin-alert info" style="margin-top:20px;">
                                <i class="fas fa-info-circle"></i>
                                Cache boyutu: ~${(JSON.stringify(localStorage).length / 1024).toFixed(1)} KB
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(overlay);

        // Volume slider event
        const volSlider = document.getElementById('defaultVolume');
        if (volSlider) {
            volSlider.addEventListener('input', function() {
                document.getElementById('volumeValue').textContent = this.value + '%';
            });
        }
    },

    switchAdminSection(section) {
        document.querySelectorAll('.admin-section').forEach(s => s.classList.remove('active'));
        document.querySelectorAll('.admin-nav-btn').forEach(b => b.classList.remove('active'));

        const sectionEl = document.getElementById('section-' + section);
        const btnEl = document.querySelector(`.admin-nav-btn[data-section="${section}"]`);

        if (sectionEl) sectionEl.classList.add('active');
        if (btnEl) btnEl.classList.add('active');
    },

    filterAdminChannels(query) {
        query = query.toLowerCase();
        const tbody = document.getElementById('adminChannelTableBody');
        const filtered = this.channels.filter(ch =>
            ch.name.toLowerCase().includes(query) ||
            ch.group.toLowerCase().includes(query)
        ).slice(0, 100);

        tbody.innerHTML = filtered.map(ch => `
            <tr data-id="${ch.id}">
                <td>${ch.number}</td>
                <td>${ch.logo ? `<img class="ch-logo" src="${ch.logo}" onerror="this.style.display='none'">` : '-'}</td>
                <td>${ch.name}</td>
                <td>${ch.group}</td>
                <td><span style="background:var(--bg-card);padding:2px 8px;border-radius:4px;font-size:11px;">${ch.type.toUpperCase()}</span></td>
                <td>
                    <div class="toggle-switch ${Storage.isChannelHidden(ch.id) ? '' : 'active'}" 
                         onclick="App.toggleChannelVisibility('${ch.id}', this)"></div>
                </td>
            </tr>
        `).join('');
    },

    toggleChannelVisibility(channelId, element) {
        const isNowVisible = Storage.toggleChannelVisibility(channelId);
        element.classList.toggle('active', isNowVisible);
    },

    savePlaylistSettings() {
        const url = document.getElementById('playlistUrl').value;
        const interval = parseInt(document.getElementById('updateInterval').value);

        Storage.updateSettings({
            m3uUrl: url,
            updateInterval: interval
        });

        alert('Playlist ayarları kaydedildi!');
    },

    async refreshPlaylist() {
        Storage.remove('channelCache');
        alert('Cache temizlendi. Sayfa yeniden yüklenecek.');
        location.reload();
    },

    addManualChannel() {
        const name = document.getElementById('manualChName').value.trim();
        const url = document.getElementById('manualChUrl').value.trim();
        const group = document.getElementById('manualChGroup').value.trim() || 'Özel';
        const logo = document.getElementById('manualChLogo').value.trim();

        if (!name || !url) {
            alert('Kanal adı ve URL gereklidir!');
            return;
        }

        const channel = {
            id: M3UParser.generateId(name, Date.now()),
            number: this.channels.length + 1,
            name: name,
            url: url,
            logo: logo,
            group: group,
            type: M3UParser.detectStreamType(url),
            isWorking: true
        };

        this.channels.push(channel);

        // Custom kanalları kaydet
        const settings = Storage.getSettings();
        settings.customChannels.push(channel);
        Storage.updateSettings(settings);

        // Cache güncelle
        Storage.setCachedChannels(this.channels);

        // UI güncelle
        this.categories = M3UParser.extractCategories(this.channels);
        this.renderCategories();
        this.filterByCategory(this.currentCategory);

        // Formu temizle
        document.getElementById('manualChName').value = '';
        document.getElementById('manualChUrl').value = '';
        document.getElementById('manualChLogo').value = '';

        alert(`"${name}" kanalı eklendi!`);
    },

    saveSettings() {
        const password = document.getElementById('adminPassword').value;
        const volume = parseInt(document.getElementById('defaultVolume').value);
        const autoplay = document.getElementById('autoplayToggle').classList.contains('active');
        const showNumbers = document.getElementById('showNumbersToggle').classList.contains('active');

        Storage.updateSettings({
            password: password,
            volume: volume,
            autoplay: autoplay,
            showChannelNumbers: showNumbers
        });

        Player.setVolume(volume);

        alert('Ayarlar kaydedildi!');
    },

    exportData() {
        const data = Storage.exportData();
        const blob = new Blob([data], { type: 'application/json' });
        const url = URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = `tivim_backup_${new Date().toISOString().split('T')[0]}.json`;
        a.click();

        URL.revokeObjectURL(url);
    },

    importData() {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = '.json';

        input.onchange = (e) => {
            const file = e.target.files[0];
            const reader = new FileReader();
            reader.onload = (ev) => {
                if (Storage.importData(ev.target.result)) {
                    alert('Veriler içe aktarıldı! Sayfa yenilenecek.');
                    location.reload();
                } else {
                    alert('İçe aktarma hatası!');
                }
            };
            reader.readAsText(file);
        };

        input.click();
    },

    importFromTextarea() {
        const data = document.getElementById('importDataArea').value;
        if (!data) {
            alert('JSON verisi yapıştırın!');
            return;
        }

        if (Storage.importData(data)) {
            alert('Veriler içe aktarıldı! Sayfa yenilenecek.');
            location.reload();
        } else {
            alert('Geçersiz JSON verisi!');
        }
    },

    resetAll() {
        if (confirm('Tüm veriler silinecek! Emin misiniz?')) {
            if (confirm('Bu işlem geri alınamaz. Devam edilsin mi?')) {
                Storage.clearAll();
                location.reload();
            }
        }
    },

    // ======== Yardımcı Fonksiyonlar ========

    isPlayerVisible() {
        return !document.getElementById('playerOverlay').classList.contains('hidden');
    },

    isAdminVisible() {
        return !!document.querySelector('.admin-overlay');
    },

    updateChannelCount() {
        document.getElementById('channelCount').textContent = `${this.channels.length} Kanal`;
    },

    updateSplash(message) {
        const status = document.getElementById('splashStatus');
        if (status) status.textContent = message;
    },

    startClock() {
        const updateClock = () => {
            const now = new Date();
            const time = now.toLocaleTimeString('tr-TR', { hour: '2-digit', minute: '2-digit' });
            document.getElementById('clock').textContent = time;

            const playerTime = document.getElementById('playerTime');
            if (playerTime) playerTime.textContent = time;
        };

        updateClock();
        setInterval(updateClock, 1000);
    },

    requestFullscreen() {
        try {
            if (!document.fullscreenElement) {
                document.documentElement.requestFullscreen().catch(() => { });
            }
        } catch (e) { }
    },

    /**
     * Event bağlama
     */
    bindEvents() {
        // Arama
        const searchInput = document.getElementById('searchInput');
        let searchTimeout;
        searchInput.addEventListener('input', (e) => {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                this.performSearch(e.target.value);
            }, 300);
        });

        searchInput.addEventListener('focus', () => {
            document.getElementById('searchBox').classList.add('focused');
        });

        searchInput.addEventListener('blur', () => {
            document.getElementById('searchBox').classList.remove('focused');
        });

        // Kategori butonları (event delegation)
        document.getElementById('categoryScroll').addEventListener('click', (e) => {
            const btn = e.target.closest('.category-btn');
            if (btn) {
                const category = btn.dataset.category;
                this.filterByCategory(category);

                document.querySelectorAll('.category-btn').forEach(b => b.classList.remove('active', 'focused'));
                btn.classList.add('active');

                setTimeout(() => {
                    const firstCard = document.querySelector('.channel-card');
                    if (firstCard) Remote.focusChannelCard(firstCard);
                }, 100);
            }
        });

        // Admin butonu
        document.getElementById('btnAdmin').addEventListener('click', () => {
            this.toggleAdmin();
        });

        // Player butonları
        document.getElementById('btnPlayPause').addEventListener('click', () => {
            Player.togglePlayPause();
        });

        document.getElementById('btnNextChannel').addEventListener('click', () => {
            this.nextChannel();
        });

        document.getElementById('btnPrevChannel').addEventListener('click', () => {
            this.prevChannel();
        });

        document.getElementById('btnToggleFav').addEventListener('click', () => {
            this.toggleCurrentFavorite();
        });

        document.getElementById('btnAspectRatio').addEventListener('click', () => {
            Player.cycleAspectRatio();
        });

        document.getElementById('btnStop').addEventListener('click', () => {
            this.closePlayer();
        });

        document.getElementById('retryBtn').addEventListener('click', () => {
            Player.retryPlay();
        });

        // Player overlay tıklama (kontrolleri göster)
        document.getElementById('videoContainer').addEventListener('click', () => {
            if (Player.controlsVisible) {
                Player.hideControls();
            } else {
                Player.showControls();
            }
        });

        // Player overlay mouse hareket
        document.getElementById('playerOverlay').addEventListener('mousemove', () => {
            Player.showControls();
        });
    }
};

// ======== BAŞLAT ========
document.addEventListener('DOMContentLoaded', () => {
    App.init();
});
