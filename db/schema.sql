create table usuario (
    id serial primary key,
    nome varchar(100) not null,
    email varchar(100) unique not null,
    endereco varchar(150),
    senha varchar(255),
    tipo boolean default false
);

create table categoria (
    id serial primary key,
    nome varchar(50) unique not null,
    descricao varchar(150)
);

create table produto (
    id serial primary key,
    nome varchar(100) not null,
    preco decimal(10,2) not null,
    foto text,
    descricao varchar(255),
    quantidade int default 0,
    id_categoria int references categoria(id)
);

create table venda (
    id serial primary key,
    id_usuario int references usuario(id) on delete cascade,
    data_hora timestamptz default current_timestamp,
    valor_total decimal(10,2),
    status varchar(30) default 'PENDENTE',
    constraint chk_status_venda check (status in ('PENDENTE', 'PAGO', 'ENVIADO', 'CANCELADO'))
);

create table venda_produto (
    id_venda int references venda(id) on delete cascade,
    id_produto int references produto(id),
    quantidade int not null,
    preco_unitario decimal(10,2) not null,
    primary key (id_venda, id_produto)
);

create table carrinho (
    id serial primary key,
    id_usuario int references usuario(id) on delete cascade,
    status varchar(10),
    data_criacao timestamptz default current_timestamp,
    constraint chk_status_carrinho check (status in ('ATIVO', 'INATIVO', 'ABANDONADO'))
);

create table carrinho_produto (
    id_carrinho int references carrinho(id) on delete cascade,
    id_produto int references produto(id),
    quantidade int not null,
    primary key (id_carrinho, id_produto)
);
